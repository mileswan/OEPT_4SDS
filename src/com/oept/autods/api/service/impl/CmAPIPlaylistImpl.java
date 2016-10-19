package com.oept.autods.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIPlaylist;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.StringUtils;
import com.oept.autods.common.util.WebServiceManager;

/**
 * @author mwan
 * Version: 1.2
 * Date: 2015/09/02
 * Description: Playlist implementor for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Service("playlistAPIService")
public class CmAPIPlaylistImpl implements CmAPIPlaylist {

	private static Map<String, Object> _playlistAPIObj = null;
	private static List<Map<String, Object>> _playlistList = new ArrayList<Map<String, Object>>();
	private static List<Map<String, Object>> _playlistItems = new ArrayList<Map<String, Object>>();
	
	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _serverURL;
	private String _apiName;
	private String _token;
	private String _apiToken;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIPlaylistImpl.class);
	
	//Constructor
	public CmAPIPlaylistImpl(){
		super();
		this._propFileMgr = new PropFileManager("interface.properties");
		this._strURI = "";
		this._serverURL = "";
		this._apiName = "";
		this._apiToken = "";
		this._wsManager = new WebServiceManager(); 
	}
	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#generateList(java.util.Map, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void generateList(Map<String, String> credential, String params)
			throws Exception {
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.listPlaylist.get");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to get response media list object
			_playlistAPIObj = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+params);
			
			//Generate media list object
			_playlistList = (List<Map<String, Object>>)_playlistAPIObj.get("list");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}

	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getPlaylistList()
	 */
	@Override
	public List<?> getPlaylistList() {
		// TODO Auto-generated method stub
		return _playlistList;
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getPlaylist(int)
	 */
	@Override
	public Map<String, Object> getPlaylist(int index) {
		// TODO Auto-generated method stub
		return _playlistList.get(index);
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getPlayListCount()
	 */
	@Override
	public int getPlayListCount() {
		// TODO Auto-generated method stub
		return (int)_playlistAPIObj.get("count");
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getPlayListOffset()
	 */
	@Override
	public int getPlayListOffset() {
		// TODO Auto-generated method stub
		return (int)_playlistAPIObj.get("offset");
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#findMediaById(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public Map<String, Object> findPlaylistById(String playlistId, String fields,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",playlistId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findPlaylist.get"), object);
			
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

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getServerURL()
	 */
	@Override
	public String getServerURL() {
		// TODO Auto-generated method stub
		return this._serverURL;
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#setServerURL(java.lang.String)
	 */
	@Override
	public void setServerURL(String serverURL) {
		// TODO Auto-generated method stub
		this._serverURL = serverURL;
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#deleteMediaById(java.lang.String, java.util.Map)
	 */
	@Override
	public Map<String, Object> deletePlaylistById(String playlistId,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",playlistId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.deletePlaylist.delete"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			response = _wsManager.sendRESTRequestDeletewithAPIToken(_strURI+_apiName);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getAPIToken()
	 */
	@Override
	public String getAPIToken() {
		// TODO Auto-generated method stub
		return this._apiToken;
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#setAPIToken(java.lang.String)
	 */
	@Override
	public void setAPIToken(String apiToken) {
		// TODO Auto-generated method stub
		this._apiToken = apiToken;
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getToken()
	 */
	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return this._token;
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#setToken(java.lang.String)
	 */
	@Override
	public void setToken(String token) {
		// TODO Auto-generated method stub
		this._token = token;
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getThumbnail(java.lang.String, java.lang.String)
	 */
	@Override
	public ResponseEntity<byte[]> getThumbnail(String thumbnailURL, String token)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			_wsManager.setToken(token);
			// Invoke webservice to invoke get thumbnail data by url
			ResponseEntity<byte[]> response = _wsManager.getWebResource(thumbnailURL);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void generatePlaylistItems(String playlistId,
			String fields, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",playlistId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.listPlaylistItems.get"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			if( fields == null || "".equals(fields)){
				response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);
			}else
			{
				response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+"?fields="+fields);
			}			
			_playlistItems = (ArrayList<Map<String, Object>>) (response).get("list");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	/* (non-Javadoc)
	 * @see com.oept.autods.cm.api.CmAPIPlaylistInt#getPlaylistItems()
	 */
	@Override
	public List<?> getPlaylistItems() {
		// TODO Auto-generated method stub
		return _playlistItems;
	}
	@Override
	public Map<String, Object> addPlaylistItems(String playlistId,
			String mediaIds, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",playlistId},
								new String[]{"\\{ids\\}",mediaIds}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.addMediaToPlaylist.put"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));

			response = _wsManager.sendRESTRequestPutwithAPIToken(_strURI+_apiName);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	@Override
	public Map<String, Object> updateSinglePlaylist(String playlistId,
			Object request, Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",playlistId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.updateSinglePlaylist.put"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));

			response = _wsManager.sendRESTRequestPutwithAPIToken(_strURI+_apiName, request);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void setPlaylistItems(List<?> playlistItems) {
		// TODO Auto-generated method stub
		_playlistItems = (List<Map<String, Object>>) playlistItems;
	}
	@Override
	public Map<String, Object> newNormalPlaylist(Object request,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.createPlaylist.post");
			
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
