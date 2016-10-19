package com.oept.autods.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIPlayer;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.StringUtils;
import com.oept.autods.common.util.WebServiceManager;
/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/07
 * Description: Player management when navigate to Marketing network page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Service("playerAPIService")
public class CmAPIPlayerImpl implements CmAPIPlayer {

	private static Map<String, Object> _playerListAPIObj = null;
	private static List<Map<String, Object>> _playerList = new ArrayList<Map<String, Object>>();
	
	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _apiName;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIPlayerImpl.class);
	
	//Constructor
	public CmAPIPlayerImpl(){
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
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.listPlayers.get");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to generate player list object
			_playerListAPIObj = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+params);
			
			//Get player list object
			_playerList = (List<Map<String, Object>>)_playerListAPIObj.get("list");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	@Override
	public List<?> getPlayerList() {
		// TODO Auto-generated method stub
		return _playerList;
	}

	@Override
	public int getPlayerListCount() {
		// TODO Auto-generated method stub
		return (int)_playerListAPIObj.get("count");
	}

	@Override
	public int getPlayerListOffset() {
		// TODO Auto-generated method stub
		return (int)_playerListAPIObj.get("offset");
	}

	@Override
	public Map<String, Object> findPlayerById(String playerId, String fields,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",playerId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findPlayerById.get"), object);
			
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
	public Map<String, Object> deletePlayerById(String playerId,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",playerId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.deletePlayerById.delete"), object);
			
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
	public Map<String, Object> updatePlayer(String playerId, Object request,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",playerId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.updatePlayer.put"), object);
			
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
	public Map<String, Object> createPlayer(Object request,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.createPlayer.post");
			
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
	public Map<String, Object> generatePlan(String uuid, 
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{uuid\\}",uuid}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.generatePlan.post"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Boolean> subParam = new HashMap<String, Boolean>();
			subParam.put("silent", true);
			params.put(uuid, subParam);

			response = _wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, params);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
}
