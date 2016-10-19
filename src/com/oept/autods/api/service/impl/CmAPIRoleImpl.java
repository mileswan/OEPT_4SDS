package com.oept.autods.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIRole;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.StringUtils;
import com.oept.autods.common.util.WebServiceManager;
/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/06
 * Description: Roles API implementation for Content Manager.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Service("roleAPIService")
public class CmAPIRoleImpl implements CmAPIRole {

	private static Map<String, Object> _rolesListAPIObj = null;
	private static List<Map<String, Object>> _rolesList = new ArrayList<Map<String, Object>>();
	
	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _apiName;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIRoleImpl.class);
	
	//Constructor
	public CmAPIRoleImpl(){
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
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.listRoles.get");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to get roles list object
			_rolesListAPIObj = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+params);
			
			//Generate user list object
			_rolesList = (List<Map<String, Object>>)_rolesListAPIObj.get("list");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	@Override
	public List<?> getRolesList() {
		// TODO Auto-generated method stub
		return _rolesList;
	}

	@Override
	public int getRolesListCount() {
		// TODO Auto-generated method stub
		return (int)_rolesListAPIObj.get("count");
	}

	@Override
	public int getRolesListOffset() {
		// TODO Auto-generated method stub
		return (int)_rolesListAPIObj.get("offset");
	}

	@Override
	public Map<String, Object> findRoleById(String roleId, String fields,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",roleId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findRoleById.get"), object);
			
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
	public Map<String, Object> deleteRoleById(String roleId,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateRole(String roleId, Object request,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> createRole(Object request,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
