package com.oept.autods.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIChannel;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.StringUtils;
import com.oept.autods.common.util.WebServiceManager;
@Service("channelAPIService")
public class CmAPIChannelImpl implements CmAPIChannel {

	private static Map<String, Object> _channelListAPIObj = null;
	private static List<Map<String, Object>> _channelList = new ArrayList<Map<String, Object>>();
	
	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _serverURL;
	private String _apiName;
	private String _token;
	private String _apiToken;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIChannelImpl.class);
	
	//Constructor
	public CmAPIChannelImpl(){
		super();
		this._propFileMgr = new PropFileManager("interface.properties");
		this._strURI = "";
		this._serverURL = "";
		this._apiName = "";
		this._apiToken = "";
		this._wsManager = new WebServiceManager(); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void generateList(Map<String, String> credential, String params)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.listChannels.get");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			// Invoke webservice to get channel list object
			_channelListAPIObj = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+params);
			
			//Generate channel list object
			_channelList = (List<Map<String, Object>>)_channelListAPIObj.get("list");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
			throw e;
		}
	}

	@Override
	public List<?> getChannelList() {
		// TODO Auto-generated method stub
		return _channelList;
	}

	@Override
	public int getChannelListCount() {
		// TODO Auto-generated method stub
		return (int)_channelListAPIObj.get("count");
	}

	@Override
	public int getChannelListOffset() {
		// TODO Auto-generated method stub
		return (int)_channelListAPIObj.get("offset");
	}

	@Override
	public Map<String, Object> findChannelById(String channelId,
			String fields, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",channelId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findChannelbyId.get"), object);
			
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
	public String getServerURL() {
		// TODO Auto-generated method stub
		return this._serverURL;
	}

	@Override
	public void setServerURL(String serverURL) {
		// TODO Auto-generated method stub
		this._serverURL = serverURL;
	}

	@Override
	public Map<String, Object> deleteChannelById(String channelId,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",channelId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.deleteChannel.delete"), object);
			
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
	public String getAPIToken() {
		// TODO Auto-generated method stub
		return this._apiToken;
	}

	@Override
	public void setAPIToken(String apiToken) {
		// TODO Auto-generated method stub
		this._apiToken = apiToken;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return this._token;
	}

	@Override
	public void setToken(String token) {
		// TODO Auto-generated method stub
		this._token = token;
	}

	@Override
	public Map<String, Object> updateChannel(String channelId,
			Object request, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",channelId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.updateChannels.put"), object);
			
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
	public Map<String, Object> createChannel(Object request,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.createChannel.post");
			
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
	public Map<String, Object> updateSchedule(String channelId, Object request, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",channelId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.updateSchedules.put"), object);
			
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
	public Map<String, Object> findTimeslots(String channelId, String frameId,
			String param, Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			_serverURL = _propFileMgr.getProperty("ContentManager.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			String[][] object={new String[]{"\\{id\\}",channelId},
					new String[]{"\\{frameId\\}",frameId}};
			_apiName = StringUtils.replace(_propFileMgr.getProperty("ContentManagerAPI.findTimeslots.get"), object);
			
			_wsManager.setAPIToken(credential.get("apiToken"));
			
			if( param == null || "".equals(param)){
				response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName);
			}else
			{
				response = _wsManager.sendRESTRequestGetwithAPIToken(_strURI+_apiName+"?"+param);
			}
				
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}

}
