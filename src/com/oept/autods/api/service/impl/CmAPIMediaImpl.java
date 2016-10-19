package com.oept.autods.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIMedia;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.StringUtils;
import com.oept.autods.common.util.WebServiceManager;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/06/18
 * Description: Media list implementor for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Service("mediaAPIService")
public class CmAPIMediaImpl implements CmAPIMedia {

	private static Map<String, Object> _mediaListAPIObj = null;
	private static List<Map<String, Object>> _mediaList = new ArrayList<Map<String, Object>>();
	
	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _serverURL;
	private String _apiName;
	private String _token;
	private String _apiToken;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIMediaImpl.class);
	
	//Constructor
	public CmAPIMediaImpl(){
		super();
		this._propFileMgr = new PropFileManager("interface.properties");
		this._strURI = "";
		this._serverURL = "";
		this._apiName = "";
		this._apiToken = "";
		this._wsManager = new WebServiceManager(); 
	}
	
	@Override
    public String getServerURL() {
        return _serverURL;
    }
	@Override
    public void setServerURL(String serverURL) {
        this._serverURL = serverURL;
    }
	@Override
    public String getAPIToken() {
        return _apiToken;
    }
	@Override
    public void setAPIToken(String apiToken) {
        this._apiToken = apiToken;
    }
	@Override
    public String getToken() {
        return _token;
    }
	@Override
    public void setToken(String token) {
        this._token = token;
    }
	
	/* (non-Javadoc)
	 * @see com.oept.autods.IO.obj.CmAPIMediaInt#generateList(String apiToken)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void generateList(Map<String,String> credential, String params) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.listMedia.get");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to get response media list object
			_mediaListAPIObj = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+params);
			
			//Generate media list object
			_mediaList = (List<Map<String, Object>>)_mediaListAPIObj.get("list");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.IO.obj.CmAPIMediaInt#getMediaList()
	 */
	@Override
	public List<Map<String, Object>> getMediaList(){
		// TODO Auto-generated method stub
		return _mediaList;
	}
	
	/* (non-Javadoc)
	 * @see com.oept.autods.IO.obj.CmAPIMediaInt#getMedia(int)
	 */
	@Override
	public Map<String, Object> getMedia(int index) {
		// TODO Auto-generated method stub
		return _mediaList.get(index);
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.IO.obj.CmAPIMediaInt#getMediaListCount()
	 */
	@Override
	public int getMediaListCount() {
		// TODO Auto-generated method stub
		return (int)_mediaListAPIObj.get("count");
	}

	/* (non-Javadoc)
	 * @see com.oept.autods.IO.obj.CmAPIMediaInt#getMediaListOffset()
	 */
	@Override
	public int getMediaListOffset() {
		// TODO Auto-generated method stub
		return (int)_mediaListAPIObj.get("offset");
	}

	@Override
	public Map<String, Object> getThumbnailStatus(String uuid, Map<String,String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			
			String[][] object={new String[]{"\\{uuid\\}",uuid}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.getThumbnailStatus.get"), object); 
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to invoke get thumbnail status by media uuid
			Map<String, Object> response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> getThumbnailStatusById(String mediaId, Map<String,String> credential) throws Exception{
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			
			String[][] object={new String[]{"\\{id\\}",mediaId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.getThumbnailStatusById.get"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to invoke get thumbnail status by media id
			Map<String, Object> response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Map<String, Object> generateThumbnail(String mediaId, Map<String,String> credential) throws Exception{
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			
			String[][] object={new String[]{"\\{mediaId\\}",mediaId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.generateThumbnail.post"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Boolean> subParam = new HashMap<String, Boolean>();
			subParam.put("silent", true);
			params.put(mediaId, subParam);		
			// Invoke webservice to invoke generate thumbnail by media id
			Map<String, Object> response = _wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, params);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Map<String, Object> findMediaById(String mediaId, String fields, Map<String,String> credential) throws Exception{
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",mediaId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findMedia.get"), object);
			
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
	public Map<String, Object> deleteMediaById(String mediaId, Map<String,String> credential) throws Exception{
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",mediaId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.deleteMedia.delete"), object);
			
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
	public ResponseEntity<byte[]> getThumbnail(String thumbnailURL, Map<String,String> credential) throws Exception{
		// TODO Auto-generated method stub
		try {
			_wsManager.setToken(credential.get("token"));

			ResponseEntity<byte[]> response = _wsManager.getWebResource(thumbnailURL);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> updateSingleMedia(String mediaId,
			Object params, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",mediaId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.updateMedia.put"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			response = _wsManager.sendRESTRequestPutwithAPIToken(_strURI+_apiName, params);
				
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> restoreMediaRevision(String mediaId,
			String mediaItemFileId, Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",mediaId},
								new String[]{"\\{mediaItemFileId\\}",mediaItemFileId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.restoreRevision.post"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Boolean> subParam = new HashMap<String, Boolean>();
			subParam.put("silent", true);
			params.put(mediaId, subParam);
			
			response = _wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, params);
				
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	/* this is create media method (new Website media) */
	@Override
	public Map<String, Object> createMedia(Map<String , String> parameter , Map<String, String> credential)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			_wsManager.setAPIToken(credential.get("apiToken"));	
			
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.createMedia.post");
			
			response = _wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, parameter);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
}
