package com.oept.autods.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIAuth;
import com.oept.autods.api.service.CmAuthLoginUser;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.WebServiceManager;

/**
 * @author mwan
 * Version: 2.0
 * Date: 2015/08/17
 * Description: Auth API implementation for Content Manager.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Service("authAPIService")
public class CmAPIAuthImpl implements CmAPIAuth {

	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _apiName;
	private String _loginToken;
	private String _apiToken;
	private CmAuthLoginUser _userlogin;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIAuthImpl.class);
	
	//Constructor
	public CmAPIAuthImpl(){
		super();
		this._propFileMgr = new PropFileManager("interface.properties");
		this._strURI = "";
		this._apiName = "";
		this._loginToken = "";
		this._apiToken = "";
		this._wsManager = new WebServiceManager(); 
		this._userlogin = new CmAuthLoginUser();
	}
	
	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return this._loginToken;
	}

	@Override
	public String getAPIToken() {
		// TODO Auto-generated method stub
		return this._apiToken;
	}

	@Override
	public Map<String, Object> login(Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.login.post");
			
			_userlogin.setUsername(credential.get("username"));
			_userlogin.setPassword(credential.get("password"));
			_userlogin.setRememberMe(true);
			      
			Map<String, Object> response = _wsManager.sendRESTRequestPost(_strURI+_apiName, _userlogin);
			_loginToken = _wsManager.getToken();
			_apiToken = _wsManager.getAPIToken();
			
			return response;		
		} catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, String> loginFromJs(Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.login.post");
			Map<String, String> response = new HashMap<String, String>();
			
			_userlogin.setUsername(credential.get("username"));
			_userlogin.setPassword(credential.get("password"));
			_userlogin.setRememberMe(true);
			      
			String responseStr = _wsManager.sendRESTRequestPostString(_strURI+_apiName, _userlogin);
			String loginStatus = _wsManager.getResponseMap().get("status").toString();
			String loginSuccess = new String("login.success");
			if(loginStatus.equals(loginSuccess)){
				@SuppressWarnings("unchecked")
				String userId = ((Map<String, Object>)_wsManager.getResponseMap().get("user")).get("id").toString();
				response.put("userId", userId);
			}	
			_loginToken = _wsManager.getToken();
			_apiToken = _wsManager.getAPIToken();
			
			response.put("responseStr", responseStr);
			response.put("loginStatus", loginStatus);		
			
			return response;		
		} catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Boolean logout(String token) throws Exception {
		// TODO Auto-generated method stub
		try{
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.logout.get");

			return (Boolean) (_wsManager.sendRESTRequestGet(_strURI+_apiName+"?token="+token, Boolean.class));
		} catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Boolean ping(String apiToken) throws Exception {
		// TODO Auto-generated method stub
		try{
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.ping.get");
			
			_wsManager.setAPIToken(apiToken);
			_wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);

			return true;
		} catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			return false;
		}
	}
}
