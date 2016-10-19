package com.oept.autods.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

/**
 * @author mwan
 * Version: 1.1
 * Date: 2015/08/10
 * Description: Playlist objects interface for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIPlaylist {
	/**
	 * Generate playlist objects
	 * @param credential name,password and tokens from session
	 * @param params   Get parameters in URL
	 */
	void generateList(Map<String, String> credential, String params) throws Exception;	
	/**
	 * Get playlist list objects
	 * @return return generated playlist list
	 */
	List<?> getPlaylistList();
	/**
	 * Get playlist from list objects
	 * @param index of playlist list
	 * 
	 * @return return playlist item map
	 */
	Map<String, Object> getPlaylist(int index);
	/**
	 * Get count property value of list objects
	 * 
	 * @return return count property value
	 */
	int getPlayListCount();
	/**
	 * Get offset property value list objects
	 * 
	 * @return return offset property value
	 */
	int getPlayListOffset();
	/**
	 * Fetch playlist info by playlist id
	 * @param playlistId playlist id
	 * @param fields comma separated fields name
	 * @param credential name,password and tokens from session
	 * 
	 * @return return playlist info map
	 */
	Map<String, Object> findPlaylistById(String playlistId, String fields, Map<String,String> credential) throws Exception;
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
	 * Delete playlist by Id
	 * @param playlistId playlist id
	 * @param credential name,password and tokens from session
	 * 
	 *  @return return delete response
	 */
	Map<String, Object> deletePlaylistById(String playlistId, Map<String, String> credential) throws Exception;
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
	 * Get thumbnail data
	 * @param thumbnailURL thumbnail url
	 * @param token  token from session
	 * @return return thumbnail data(img/png)
	 */
	ResponseEntity<byte[]> getThumbnail(String thumbnailURL, String token) throws Exception;
	/**
	 * Fetch playlist item list by playlist id
	 * @param playlistId playlist id
	 * @param fields comma separated fields name
	 * @param credential name,password and tokens from session
	 * 
	 */
	void generatePlaylistItems(String playlistId, String fields, Map<String,String> credential) throws Exception;
	/**
	 * Add media items to playlist by playlist id
	 * @param playlistId playlist id
	 * @param mediaIds comma separated media Ids
	 * @param credential name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> addPlaylistItems(String playlistId, String mediaIds, Map<String,String> credential) throws Exception;
	/**
	 * Add media items to playlist by playlist id
	 * @param playlistId playlist id
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return status values
	 */
	Map<String, Object> updateSinglePlaylist(String playlistId, Object request, Map<String,String> credential) throws Exception;
	/**
	 * Get playlist items list objects
	 * @return return generated playlist items list
	 */
	List<?> getPlaylistItems();
	/**
	 * Get playlist items list objects
	 * @param playlistItems playlist items list object
	 */
	void setPlaylistItems(List<?> playlistItems);
	/**
	 * create normal playlist
	 * @param request request body object
	 * @param credential user name,password and tokens from session
	 * 
	 * @return return created playlist object
	 */
	Map<String, Object> newNormalPlaylist(Object request, Map<String,String> credential) throws Exception;
}
