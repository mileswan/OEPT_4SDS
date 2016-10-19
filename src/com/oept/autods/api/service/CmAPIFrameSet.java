package com.oept.autods.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/07
 * Description: FrameSet objects interface for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIFrameSet {
	/**
	 * Generate frameset list objects
	 * @param credential name,password and tokens from session
	 * @param params Get parameters in URL
	 */
	void generateList(Map<String, String> credential, String params) throws Exception;	
	/**
	 * Get channel list objects
	 * @return return generated channel list
	 */
	List<?> getFrameSetList();
	/**
	 * Get count property value of list objects
	 * 
	 * @return return count property value
	 */
	int getFrameSetListCount();
	/**
	 * Get offset property value list objects
	 * 
	 * @return return offset property value
	 */
	int getFrameSetListOffset();
	/**
	 * Fetch FrameSet info by FrameSet id
	 * @param frameSetId frameset id
	 * @param fields comma separated fields name
	 * @param credential name,password and tokens from session
	 * 
	 * @return return channel info map
	 */
	Map<String, Object> findFrameSetById(String frameSetId, String fields, Map<String,String> credential) throws Exception;
	/**
	 * Delete frameset by Id
	 * @param frameSetId frameset id
	 * @param credential name,password and tokens from session
	 * 
	 *  @return return delete response
	 */
	Map<String, Object> deleteFrameSetById(String frameSetId, Map<String, String> credential) throws Exception;
	/**
	 * Update FrameSet info
	 * @param frameSetId frameset id
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> updateFrameSet(String frameSetId, Object request, Map<String,String> credential) throws Exception;
	/**
	 * create FrameSet
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return created FrameSet object
	 */
	Map<String, Object> createFrameSet(Object request, Map<String,String> credential) throws Exception;
}
