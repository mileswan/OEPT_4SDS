package com.oept.autods.api.service;

import java.util.Map;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/02
 * Description: Channels objects interface for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIStorage {
	/**
	 * Returns the stored value.
	 * @param channelId channel id
	 * @param credential name,password and tokens from session
	 * 
	 * @return return stored value info map
	 */
	Map<String, Object> getStoredValue(String uuid,  Map<String,String> credential) throws Exception;
	/**
	 * Store value.
	 * @param storeValue Any string can be store. Including JSON string. Maximum characters is 10240.
	 * @param credential name,password and tokens from session
	 * 
	 *  @return return store value response
	 */
	Map<String, Object> postStoreValue(Object storeValue, Map<String, String> credential) throws Exception;
}
