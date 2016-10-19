package com.oept.autods.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/02
 * Description: Channels objects interface for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIChannel {
	/**
	 * Generate channels list objects
	 * @param credential name,password and tokens from session
	 * @param params Get parameters in URL
	 */
	void generateList(Map<String, String> credential, String params) throws Exception;	
	/**
	 * Get channel list objects
	 * @return return generated channel list
	 */
	List<?> getChannelList();
	/**
	 * Get count property value of list objects
	 * 
	 * @return return count property value
	 */
	int getChannelListCount();
	/**
	 * Get offset property value list objects
	 * 
	 * @return return offset property value
	 */
	int getChannelListOffset();
	/**
	 * Fetch channel info by channel id
	 * @param channelId channel id
	 * @param fields comma separated fields name
	 * @param credential name,password and tokens from session
	 * 
	 * @return return channel info map
	 */
	Map<String, Object> findChannelById(String channelId, String fields, Map<String,String> credential) throws Exception;
	/**
	 * Get Content Manager url
	 * 
	 * @return return Content Manager url
	 */
	String getServerURL();
	/**
	 * Set Content Manager url
	 * @param serverURL server url
	 * 
	 */
	void setServerURL(String serverURL);
	/**
	 * Delete channel by Id
	 * @param channelId channel id
	 * @param credential name,password and tokens from session
	 * 
	 *  @return return delete response
	 */
	Map<String, Object> deleteChannelById(String channelId, Map<String, String> credential) throws Exception;
	/**
	 * Get session api token
	 * 
	 * @return return session api token
	 */
	String getAPIToken();
	/**
	 * Set session api token
	 * @param apiToken session api token
	 * 
	 */
	void setAPIToken(String apiToken);
	/**
	 * Get session token
	 * 
	 * @return return session token
	 */
	String getToken();
	/**
	 * Set session token
	 * @param apiToken session token
	 * 
	 */
	void setToken(String token);
	/**
	 * Update channel info
	 * @param channelId channel id
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> updateChannel(String channelId, Object request, Map<String,String> credential) throws Exception;
	/**
	 * create channel
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return created channel object
	 */
	Map<String, Object> createChannel(Object request, Map<String,String> credential) throws Exception;
	/**
	 * Update schedule info
	 * @param channelId channel id
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> updateSchedule(String channelId, Object request, Map<String,String> credential) throws Exception;
	/**
	 * Find timeslots info by channel id and frame id
	 * @param channelId channel id
	 * @param frameId frame id
	 * @param param query parameters
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> findTimeslots(String channelId, String frameId, String param, Map<String,String> credential) throws Exception;
}
