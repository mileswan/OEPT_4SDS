package com.oept.autods.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oept.autods.api.service.CmAPIAuth;
import com.oept.autods.api.service.CmAPIFileUpload;
import com.oept.autods.api.service.CmAPIMedia;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/07/23
 * Description: Upload files controller.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/fileupload")
public class FileUploadAct {

	private static final Log logger = LogFactory.getLog(FileUploadAct.class);
	@Qualifier("fileUploadAPIService")
	@Autowired
	private CmAPIFileUpload fileUploadAPIService;
	@Qualifier("mediaAPIService")
	@Autowired
	private CmAPIMedia mediaAPIService;
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	
	//Batch file action
	@RequestMapping(value="/batchupload.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> handleFileUpload(@RequestParam("files[]") List<MultipartFile> files,HttpServletRequest request, HttpSession session) throws Exception{
		String errMsg = "";
		Map<String, Object> fileMap = new HashMap<String, Object>();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> filesList = new ArrayList<Map<String, Object>>();
		
		if(authAPIService.ping(session.getAttribute("apiToken").toString())){		
			String strUUID = "";
			String strMediaId = "";
						
			Map<String, Object> fileUploadRequest = new HashMap<String, Object>();
			Map<String, Object> fileUploadResponse = new HashMap<String, Object>();
			Map<String, Object> mediaMap = new HashMap<String, Object>();			

			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());
			credential.put("apiToken", session.getAttribute("apiToken").toString());

			for (int i =0; i< files.size(); i++) {
				MultipartFile file = files.get(i);
				String fileName = file.getOriginalFilename();

				//            File dirFile = new File(path);
				//            if(!dirFile.exists())
				//            {
				//            	logger.info("The folder "+path+" do  not exist,now trying to create a one...");
				//            	if(dirFile.mkdir()){
				//            		logger.info("Create folder "+path+" successfully!");
				//            	}else
				//            		logger.info("Create folder "+path+" failed!");
				//            }

				if (!file.isEmpty()) {
					try {
						byte[] bytes = file.getBytes();
						//                    BufferedOutputStream stream =
						//                            new BufferedOutputStream(new FileOutputStream(new File(path+fileName)));
						//                    stream.write(bytes);
						//                    stream.close();

						fileMap.put("name", fileName);                  
						fileMap.put("size", file.getSize());                   
						fileUploadRequest.put("filename", fileName);
						fileUploadRequest.put("filepath", "");
						fileUploadRequest.put("uploadType", "media_item");
						fileUploadResponse = fileUploadAPIService.initUpload(fileUploadRequest, credential);

						strUUID = fileUploadResponse.get("uuid").toString();
						strMediaId = fileUploadResponse.get("mediaId").toString();

						fileUploadRequest.put("uploadId", strUUID);
						fileUploadRequest.put("offset", "0");
						fileUploadRequest.put("filedata", bytes);
						fileUploadAPIService.uploadFilePart(fileUploadRequest, credential);

						fileUploadAPIService.uploadFinished(fileUploadRequest, credential);

						mediaMap = mediaAPIService.findMediaById(strMediaId, null, credential);

						fileMap.put("url", mediaAPIService.getServerURL() + mediaMap.get("downloadPath"));

						filesList.add(fileMap);

					} catch (Exception e) {
						logger.info(e);
						errMsg = "You failed to upload " + fileName + " => " + e.getMessage();
						logger.info(errMsg);
						fileMap.clear();
						fileMap.put("error", errMsg); 
						filesList.add(fileMap);
					}
				} else {
					errMsg = "You failed to upload " + fileName + " because the file was empty.";
					logger.info(errMsg); 
					fileMap.clear();
					fileMap.put("error", errMsg);
					filesList.add(fileMap);
				}
			}
		}else{
			errMsg = "You failed to upload because session has been invalidate.";
			logger.info(errMsg); 
			fileMap.clear();
			fileMap.put("error", errMsg);
			filesList.add(fileMap);
		}

			responseMap.put("files", filesList);
			return responseMap;
		
    }
	
	//Media upload page
	@RequestMapping(value="/mediaupload.do", method = RequestMethod.GET)
	public String navToMediaUpload(Model model, HttpServletRequest request, HttpSession session){
		
		ResourceBundle rb = ResourceBundle.getBundle("language/messages", Locale.CHINA);
		String uploadType = rb.getString("fileUploadType");
		
		model.addAttribute("uploadType", uploadType);
		
		return "fileUpload";
		
	}
}
