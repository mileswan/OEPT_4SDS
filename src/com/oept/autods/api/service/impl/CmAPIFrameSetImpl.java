package com.oept.autods.api.service.impl;
/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/07
 * Description: Frameset management when navigate to Marketing network page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIFrameSet;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.StringUtils;
import com.oept.autods.common.util.WebServiceManager;
@Service("frameSetAPIService")
public class CmAPIFrameSetImpl implements CmAPIFrameSet {

	private static Map<String, Object> _frameSetListAPIObj = null;
	private static List<Map<String, Object>> _frameSetList = new ArrayList<Map<String, Object>>();
	
	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _apiName;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIChannelImpl.class);
	
	//Constructor
	public CmAPIFrameSetImpl(){
		super();
		this._propFileMgr = new PropFileManager("interface.properties");
		this._strURI = "";
		this._apiName = "";;
		this._wsManager = new WebServiceManager(); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void generateList(Map<String, String> credential, String params)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.listFrameSet.get");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to get frameset list object
			_frameSetListAPIObj = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+params);
			
			//Generate frameset list object
			_frameSetList = (List<Map<String, Object>>)_frameSetListAPIObj.get("list");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	@Override
	public List<?> getFrameSetList() {
		// TODO Auto-generated method stub
		return _frameSetList;
	}

	@Override
	public int getFrameSetListCount() {
		// TODO Auto-generated method stub
		return (int)_frameSetListAPIObj.get("count");
	}

	@Override
	public int getFrameSetListOffset() {
		// TODO Auto-generated method stub
		return (int)_frameSetListAPIObj.get("offset");
	}

	@Override
	public Map<String, Object> findFrameSetById(String frameSetId,
			String fields, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",frameSetId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findFrameSetById.get"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			if( fields == null || "".equals(fields)){
				response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);
			}else
			{
				response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+"?fields="+fields);
			}			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> deleteFrameSetById(String frameSetId,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",frameSetId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.deleteFrameSetById.delete"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));	
			
			response = _wsManager.sendRESTRequestDeletewithAPIToken(_strURI+_apiName);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> updateFrameSet(String frameSetId,
			Object request, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",frameSetId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.updateFrameSet.put"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			response = _wsManager.sendRESTRequestPutwithAPIToken(_strURI+_apiName, request);
				
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> createFrameSet(Object request,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.createFrameSet.post");
			
			_wsManager.setAPIToken(credential.get("apiToken"));

			response = _wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, request);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

}
