package com.oept.autods.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.oept.autods.api.service.CmAPIStorage;
import com.oept.autods.common.util.PropFileManager;
import com.oept.autods.common.util.WebServiceManager;
@Service("storageAPIService")
public class CmAPIStorageImpl implements CmAPIStorage {
	
	private PropFileManager _propFileMgr;
	private String _strURI;
	private String _apiName;
	private WebServiceManager _wsManager; 
	private static final Log logger = LogFactory.getLog(CmAPIStorageImpl.class);
	
	//Constructor
	public CmAPIStorageImpl(){
		super();
		this._propFileMgr = new PropFileManager("interface.properties");
		this._strURI = "";
		this._apiName = "";
		this._wsManager = new WebServiceManager(); 
	}
	@Override
	public Map<String, Object> getStoredValue(String uuid,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> postStoreValue(Object storeValue,
			Map<String, String> credential) throws Exception {
		// TODO Auto-generated method stub
		try {
			_strURI = _propFileMgr.getProperty("ContentManagerAPI.uri");
			Map<String, Object> response = new HashMap<String, Object>();
			
			_apiName = _propFileMgr.getProperty("ContentManagerAPI.storeValue.post");
			
			_wsManager.setAPIToken(credential.get("apiToken"));

			response = _wsManager.sendRESTRequestPostwithAPIToken(_strURI+_apiName, storeValue);
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
}
