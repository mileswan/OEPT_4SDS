package com.oept.autods.api.service;

import java.util.Map;

/**
 * @author mwan
 * Version: 1.5
 * Date: 2015/08/17
 * Description: Auth API for Content Manager.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIAuth {
	/**
	 * Invoke login api
	 * @param user name and password
	 * 
	 * @return return login response map data
	 * @throws Exception 
	 */
	Map<String, Object> login(Map<String, String> credential) throws Exception;
	/**
	 * Invoke login api from JS
	 * @param user name and password
	 * 
	 * @return return login response json data
	 * @throws Exception 
	 */
	Map<String, String> loginFromJs(Map<String, String> credential) throws Exception;
	/**
	 * Refresh token with user credential
	 * 
	 * @return return refreshed token��
	 */
	String getToken();
	/**
	 * Refresh token with user credential
	 * 
	 * @return return refreshed api token��
	 */
	String getAPIToken();
	/**
	 * Invoke logout api
	 * @param login token
	 * 
	 * @return return if logout succeed
	 * @throws Exception 
	 */
	Boolean logout(String token) throws Exception;
	/**
	 * Invoke ping server api
	 * @param apiToken api token from session
	 * 
	 * @return return if ping succeed
	 * @throws Exception 
	 */
	Boolean ping(String apiToken) throws Exception;
}
