package com.oept.autods.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/06
 * Description: User's roles interface for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIRole {
	/**
	 * Generate roles list objects
	 * @param credential name,password and tokens from session
	 * @param params Get parameters in URL
	 */
	void generateList(Map<String, String> credential, String params) throws Exception;	
	/**
	 * Get roles list objects
	 * @return return generated roles list
	 */
	List<?> getRolesList();
	/**
	 * Get count property value of list objects
	 * 
	 * @return return count property value
	 */
	int getRolesListCount();
	/**
	 * Get offset property value list objects
	 * 
	 * @return return offset property value
	 */
	int getRolesListOffset();
	/**
	 * Fetch role info by role id
	 * @param roleId role id
	 * @param fields comma separated fields name
	 * @param credential name,password and tokens from session
	 * 
	 * @return return channel info map
	 */
	Map<String, Object> findRoleById(String roleId, String fields, Map<String,String> credential) throws Exception;
	/**
	 * Delete role by Id
	 * @param roleId role id
	 * @param credential name,password and tokens from session
	 * 
	 *  @return return delete response
	 */
	Map<String, Object> deleteRoleById(String roleId, Map<String, String> credential) throws Exception;
	/**
	 * Update role info
	 * @param roleId role id
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> updateRole(String roleId, Object request, Map<String,String> credential) throws Exception;
	/**
	 * create role
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return created role object
	 */
	Map<String, Object> createRole(Object request, Map<String,String> credential) throws Exception;
}
