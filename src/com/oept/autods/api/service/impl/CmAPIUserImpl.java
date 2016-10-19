package com.oept.autods.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIUser;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.StringUtils;
import com.oept.autods.common.util.WebServiceManager;
import com.oept.autods.service.UserService;
/**
 * @author mwan
 * Version: 2.0
 * Date: 2015/09/05
 * Description: Users API implementation for Content Manager.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Service("userAPIService")
public class CmAPIUserImpl implements CmAPIUser {

	private static Map<String, Object> _userListAPIObj = null;
	private static List<Map<String, Object>> _userList = new ArrayList<Map<String, Object>>();
	
//	private List<Map<String, Object>> _userPositionList = new ArrayList<Map<String, Object>>();
	
	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _apiName;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIUserImpl.class);
	
	//注解
	@Qualifier("UserService")
	@Autowired
	private UserService userService;
	
	
	//Constructor
	public CmAPIUserImpl(){
		super();
		this._propFileMgr = new PropFileManager("interface.properties");
		this._strURI = "";
		this._apiName = "";
		this._wsManager = new WebServiceManager(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public void generateList(Map<String, String> credential, String params)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.listUser.get");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to get user list object
			_userListAPIObj = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+params);
			
			//Generate user list object
			_userList = (List<Map<String, Object>>)_userListAPIObj.get("list");
			
			/*
			 * 从oept数据库中读取职位信息放入_userList对象中
			 * 用以显示职位信息
			 * */
			for(int i=0;i<_userList.size();i++){
				int id = (int)_userList.get(i).get("id");
				_userList.get(i).put("uic_position_name", userService.selectPosition(id).get("uic_position_name"));
				_userList.get(i).put("uic_position_id", userService.selectPosition(id).get("uic_position_id"));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	@Override
	public List<?> getUserList() {
		// TODO Auto-generated method stub
		return _userList;
	}

	@Override
	public int getUserListCount() {
		// TODO Auto-generated method stub
		return (int)_userListAPIObj.get("count");
	}

	@Override
	public int getUserListOffset() {
		// TODO Auto-generated method stub
		return (int)_userListAPIObj.get("offset");
	}
	
	@Override
	public Map<String, Object> findUserPropByName(String userName, Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try{
		_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");	
		String[][] object={new String[]{"\\{name\\}",userName}};
		_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findUserByName.get"), object);
		
		_wsManager.setAPIToken(credential.get("apiToken"));
		
		// Invoke webservice to get response media list object
		Map<String, Object> response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);
		return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> findUserById(String userId, Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");	
			String[][] object={new String[]{"\\{id\\}",userId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findUserById.get"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to get response media list object
			Map<String, Object> response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);
			return response;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				throw e;
			}
	}

	@Override
	public Map<String, Object> updateCurrentUser(String userId,
			Map<String, Object> requestBody, Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");	
			String[][] object={new String[]{"\\{id\\}",userId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.userSettings.put"), object);

			_wsManager.setAPIToken(credential.get("apiToken"));

			// Invoke webservice to update current user info
			Map<String, Object> response = _wsManager.sendRESTRequestPutwithAPIToken(_strURI+_apiName, requestBody);
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Map<String, Object> getCurrentUser(Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");	
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.userSettings.get");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to get response current user info
			Map<String, Object> response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);
			return response;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				throw e;
			}
	}
	
	@Override
	public Map<String, Object> deleteUserById(String userId, Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			
			String[][] object={new String[]{"\\{id\\}",userId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.deleteUserById.delete"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to delete user
			Map<String, Object> response = _wsManager.sendRESTRequestDeletewithAPIToken(_strURI+_apiName);
			return response;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				throw e;
			}
	}

	@Override
	public Map<String, Object> createUser(Object request, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.createUser.post");
			
			_wsManager.setAPIToken(credential.get("apiToken"));

			response = _wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, request);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> updateUserById(String userId, Map<String, Object> requestBody, Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");	
			String[][] object={new String[]{"\\{id\\}",userId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.updateUser.put"), object);

			_wsManager.setAPIToken(credential.get("apiToken"));

			// Invoke webservice to update user by Id
			Map<String, Object> response = _wsManager.sendRESTRequestPutwithAPIToken(_strURI+_apiName, requestBody);
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
}
