package com.oept.autods.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/07
 * Description: Player objects interface for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIPlayer {
	/**
	 * Generate player list objects
	 * @param credential name,password and tokens from session
	 * @param params Get parameters in URL
	 */
	void generateList(Map<String, String> credential, String params) throws Exception;	
	/**
	 * Get channel list objects
	 * @return return generated channel list
	 */
	List<?> getPlayerList();
	/**
	 * Get count property value of list objects
	 * 
	 * @return return count property value
	 */
	int getPlayerListCount();
	/**
	 * Get offset property value list objects
	 * 
	 * @return return offset property value
	 */
	int getPlayerListOffset();
	/**
	 * Fetch player info by player id
	 * @param playerId player id
	 * @param fields comma separated fields name
	 * @param credential name,password and tokens from session
	 * 
	 * @return return channel info map
	 */
	Map<String, Object> findPlayerById(String playerId, String fields, Map<String,String> credential) throws Exception;
	/**
	 * Delete player by Id
	 * @param playerId player id
	 * @param credential name,password and tokens from session
	 * 
	 *  @return return delete response
	 */
	Map<String, Object> deletePlayerById(String playerId, Map<String, String> credential) throws Exception;
	/**
	 * Update Player info
	 * @param playerId player id
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> updatePlayer(String playerId, Object request, Map<String,String> credential) throws Exception;
	/**
	 * create player
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return created player object
	 */
	Map<String, Object> createPlayer(Object request, Map<String,String> credential) throws Exception;
	/**
	 * Generate plan for one or more Players
	 * @param uuid UUID representation of one or more player ID
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> generatePlan(String uuid, Map<String,String> credential) throws Exception;
}
