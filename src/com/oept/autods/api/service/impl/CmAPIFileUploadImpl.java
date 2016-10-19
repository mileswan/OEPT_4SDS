package com.oept.autods.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIFileUpload;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.StringUtils;
import com.oept.autods.common.util.WebServiceManager;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/07/23
 * Description: Invoke Content Manager API implementation for file upload.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Service("fileUploadAPIService")
public class CmAPIFileUploadImpl implements CmAPIFileUpload {

	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _apiName;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIFileUploadImpl.class);

	//Constructor
	public CmAPIFileUploadImpl(){
		super();
		this._propFileMgr = new PropFileManager("interface.properties");
		this._strURI = "";
		this._apiName = "";
		this._wsManager = new WebServiceManager(); 
	}
	
	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIFileUploadInt#initUpload(java.util.Map)
	 */
	@Override
	public Map<String, Object> initUpload(Map<String, Object> request,Map<String,String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.fileupload.init");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to invoke file upload
			Map<String, Object> response = _wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, request);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIFileUploadInt#uploadFilePart(java.util.Map)
	 */
	@Override
	public void uploadFilePart(Map<String, Object> request, Map<String,String> credential) throws Exception{
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			
			String uploadId = request.get("uploadId").toString();
			String offset = request.get("offset").toString();
			String[][] object={new String[]{"\\{uploadId\\}",uploadId},new String[]{"\\{offset\\}",offset}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.fileupload.put"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to invoke file part upload
			_wsManager.sendRESTRequestPutwithAPIToken(_strURI+_apiName, request.get("filedata"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIFileUploadInt#uploadSingleFile(java.util.Map)
	 */
	@Override
	public void uploadSingleFile(Map<String, Object> request, Map<String,String> credential) throws Exception{
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			
			String uploadId = request.get("uploadId").toString();
			String offset = request.get("offset").toString();
			String[][] object={new String[]{"\\{uploadId\\}",uploadId},new String[]{"\\{offset\\}",offset}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.fileupload.post"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to invoke file part upload
			_wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, request.get("filedata"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIFileUploadInt#uploadFinished(java.util.Map)
	 */
	@Override
	public void uploadFinished(Map<String, Object> request, Map<String,String> credential) throws Exception{
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			
			String uploadId = request.get("uploadId").toString();
			String[][] object={new String[]{"\\{uploadId\\}",uploadId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.fileupload.complete.post"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Boolean> subParam = new HashMap<String, Boolean>();
			subParam.put("silent", true);
			params.put(uploadId, subParam);
			// Invoke webservice to invoke file upload complete service
			_wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, params);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}
}
