package com.oept.autods.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author mwan
 * Version: 1.5
 * Date: 2015/08/18
 * Description: Users API for Content Manager.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIUser {
	/**
	 * Generate user list objects
	 * @param credential name,password and tokens from session
	 * @param params Get parameters in URL
	 */
	void generateList(Map<String, String> credential, String params) throws Exception;	
	/**
	 * Get user list objects
	 * @return return generated user list
	 */
	List<?> getUserList();
	/**
	 * Get count property value of list objects
	 * 
	 * @return return count property value
	 */
	int getUserListCount();
	/**
	 * Get offset property value list objects
	 * 
	 * @return return offset property value
	 */
	int getUserListOffset();
	/**
	 * Invoke find user by name api
	 * @param username to be fetched
	 * @param user name and password
	 * 
	 * @return return user response map data
	 * @throws Exception 
	 */
	Map<String, Object> findUserPropByName(String userName, Map<String, String> credential) throws Exception;
	/**
	 * Invoke find user by id api
	 * @param user id to be fetched
	 * @param user name and password
	 * 
	 * @return return user response map data
	 * @throws Exception 
	 */
	Map<String, Object> findUserById(String userId, Map<String, String> credential) throws Exception;
	/**
	 * Invoke update user by name api
	 * @param user id to update
	 * @param request body of update info
	 * @param user name and password
	 * 
	 * @return return user response map data
	 * @throws Exception 
	 */
	Map<String, Object> updateCurrentUser(String userId, Map<String, Object> requestBody, Map<String, String> credential) throws Exception;
	/**
	 * Invoke get current user
	 * @param user name and password
	 * 
	 * @return return user response map data
	 * @throws Exception 
	 */
	Map<String, Object> getCurrentUser(Map<String, String> credential) throws Exception;
	/**
	 * Delete user by Id
	 * @param userId user id
	 * @param credential name,password and tokens from session
	 * 
	 *  @return return delete response
	 */
	Map<String, Object> deleteUserById(String userId, Map<String, String> credential) throws Exception;
	/**
	 * create user
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return created user object
	 */
	Map<String, Object> createUser(Object request, Map<String,String> credential) throws Exception;
	/**
	 * Invoke update user by Id
	 * @param user id to update
	 * @param request body of update info
	 * @param user name and password
	 * 
	 * @return return user response map data
	 * @throws Exception 
	 */
	Map<String, Object> updateUserById(String userId, Map<String, Object> requestBody, Map<String, String> credential) throws Exception;
	
}
