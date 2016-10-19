package com.oept.autods.action;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oept.autods.api.service.CmAPIAuth;
import com.oept.autods.api.service.CmAPIMedia;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/06/18
 * Description: Media management when navigate to Marketing media page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/media")
public class MediaAct {
	
	private static final Log logger = LogFactory.getLog(MediaAct.class);
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	@Qualifier("mediaAPIService")
	@Autowired
	private CmAPIMedia mediaAPIService;
	
	@RequestMapping(value="/list.do")
	public String navToMediaList(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());			
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				mediaAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				model.addAttribute("mediaList", mediaAPIService.getMediaList());
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "mediaList";		
	}
	
	@RequestMapping(value="/approval.do")
	public String navToMediaApprovalList(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());			
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String filter = "{'approvalStatus':{'values':['PENDING_APPROVAL','REJECTED']}}";
				String filterEncode = URLEncoder.encode(filter,"UTF-8");
				String urlParams = "?limit=0&offset=0&sort=name&filters="+filterEncode;
				mediaAPIService.generateList(credential, urlParams);
				
				model.addAttribute("mediaApprovalList", mediaAPIService.getMediaList());
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "mediaApprovalList";		
	}
	
	@RequestMapping(value="/approvalrules.do")
	public String navToMediaApprovalRules(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		return "mediaApprovalRules";		
	}
	
	@RequestMapping(value="/details.do")
	public String navToMediaDetail(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());
			String mediaId = request.getParameter("mediaId");
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				Map<String,Object> mediaDetail = mediaAPIService.findMediaById(mediaId, null, credential);
				
				if(mediaDetail.get("webDavPath") != null){
					String webDavPath = mediaDetail.get("webDavPath").toString();
					int i = webDavPath.lastIndexOf("content/");
					String previewURI = webDavPath.substring(i+7,webDavPath.length());
					mediaDetail.put("previewURI", previewURI);
				}			
				model.addAttribute("mediaId", mediaId);
				model.addAttribute("mediaDetail", mediaDetail);
				
				if((boolean) mediaDetail.get("audioDucking")){
					model.addAttribute("audioDucking_true", "active");
				}else{
					model.addAttribute("audioDucking_false", "active");
				}
				if((boolean) mediaDetail.get("playFullscreen")){
					model.addAttribute("playFullscreen_true", "active");
				}else{
					model.addAttribute("playFullscreen_false", "active");
				}
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "mediaDetails";		
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public String deleteMediaById(HttpServletRequest request, HttpSession session) throws Exception{
		
		String mediaIds = request.getParameter("mediaIds");
		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());			
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String mediaId[] = mediaIds.split(",");
				for(int i=0;i<mediaId.length;i++){
					mediaAPIService.deleteMediaById(mediaId[i], credential);
				}
				
				return "delete.success";
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	//This method used to get thumbnail or preview data
	@RequestMapping(value="/getThumbnail.do", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getThumbnail(HttpServletRequest request, HttpSession session) throws Exception{
		
		String thumbnailURL = request.getParameter("thumnailURL");
		ResponseEntity<byte[]> response;		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("token", session.getAttribute("token").toString());
				response = mediaAPIService.getThumbnail(thumbnailURL, credential);
			}else{
				return null;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}	
		
		return response;
	}

	@RequestMapping(value="/updateSingle.do")
	@ResponseBody
	public String updateSingleMedia(HttpServletRequest request, HttpSession session) throws Exception{
		
		String mediaId = request.getParameter("mediaId");
		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());
			
			Map<String, Object> requestBody = new HashMap<String, Object>();
			Map<String, Object> responseBody = new HashMap<String, Object>();
			
			String description = request.getParameter("description");
			String startValidDate = request.getParameter("startValidDate");
			String endValidDate = request.getParameter("endValidDate");
			Boolean audioDucking = Boolean.parseBoolean(request.getParameter("audioDucking"));
			Boolean playFullscreen = Boolean.parseBoolean(request.getParameter("playFullscreen"));
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				requestBody.put("description", description);
				requestBody.put("startValidDate", startValidDate);
				requestBody.put("endValidDate", endValidDate);
				requestBody.put("audioDucking", audioDucking);
				requestBody.put("playFullscreen", playFullscreen);
				responseBody = mediaAPIService.updateSingleMedia(mediaId, requestBody, credential);
			}else{
				return "redirect:/auth/logout.do";
			}
			
			ObjectMapper om = new ObjectMapper();
			String responseStr = om.writeValueAsString(responseBody);		
			
			return responseStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/restore.do")
	public String restoreMediaRevision(HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());
			
			String mediaId = request.getParameter("mediaId");
			String mediaItemFileId = request.getParameter("mediaItemFileId");
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				mediaAPIService.restoreMediaRevision(mediaId, mediaItemFileId, credential);
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "mediaDetails";		
	}
	
	@RequestMapping(value="/addMediaUrl.do")
	public String createNewMediaUrl(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response)throws Exception{
		String addMediaName; 
		String addMediaUrl;
		String addMediaType = "HTML";
		
		addMediaName = request.getParameter("addMediaName");
		addMediaUrl = request.getParameter("addMediaUrl");
		
		try {
			//Authentication token
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());			
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				//new media body
				Map<String,String> parameter = new HashMap<String,String>();
				parameter.put("name", addMediaName);
				parameter.put("uri", addMediaUrl);
				parameter.put("mediaType", addMediaType);
				mediaAPIService.createMedia(parameter,credential);
//				mediaAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
//				model.addAttribute("mediaList", mediaAPIService.getMediaList());
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		//调用显示列表方法
		navToMediaList(model,request,session,response);
		return "mediaList";
	}
	

}
