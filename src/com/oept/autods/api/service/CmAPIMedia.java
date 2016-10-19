package com.oept.autods.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

/**
 * @author mwan
 * Version: 1.1
 * Date: 2015/07/23
 * Description: Media objects interface for Content Manager API.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIMedia {
	/**
	 * Generate media list objects
	 * @param credential   user name and password from session
	 * @param params   Get parameters in URL
	 */
	void generateList(Map<String, String> credential, String params) throws Exception;	
	/**
	 * Get media list objects
	 * @return return generated media list
	 */
	List<?> getMediaList();
	/**
	 * Get media from list objects
	 * @param index of media list
	 * 
	 * @return return media item map
	 */
	Map<String, Object> getMedia(int index);
	/**
	 * Get count property value of list objects
	 * 
	 * @return return count property value
	 */
	int getMediaListCount();
	/**
	 * Get offset property value list objects
	 * 
	 * @return return offset property value
	 */
	int getMediaListOffset();
	/**
	 * Fetch media info by media id
	 * @param mediaId media id
	 * @param credential user name and password or tokens from session
	 * @param fields id,name,description,lastModified,thumbnailDownloadPaths,downloadPath ...
	 * 
	 * @return return media info map
	 */
	Map<String, Object> findMediaById(String mediaId, String fields, Map<String,String> credential) throws Exception;
	/**
	 * Update single media info by media id
	 * @param mediaId media id
	 * @param credential user name, password or tokens from session
	 * @param params request body which has media info
	 * 
	 * @return return media info map
	 */
	Map<String, Object> updateSingleMedia(String mediaId, Object params, Map<String,String> credential) throws Exception;
	/**
	 * Get thumbnail status by uuid
	 * @param uuid for the thumbnail generation operation
	 * @param credential user name and password from session
	 * 
	 * @return return thumbnail status map
	 */
	Map<String, Object> getThumbnailStatus(String uuid, Map<String,String> credential) throws Exception;
	/**
	 * Get thumbnail status by media id
	 * @param mediaId media id
	 * @param credential user name and password from session
	 * 
	 * @return return thumbnail status map
	 */
	Map<String, Object> getThumbnailStatusById(String mediaId, Map<String,String> credential) throws Exception;
	/**
	 * Generate thumbnail by media id
	 * @param mediaId media id
	 * @param credential user name and password from session
	 * 
	 * @return return thumbnail status map
	 */
	Map<String, Object> generateThumbnail(String mediaId, Map<String, String> credential) throws Exception;
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
	 * Delete media by Id
	 * @param mediaId media id
	 * @param credential user name and password from session
	 * 
	 *  @return return delete response
	 */
	Map<String, Object> deleteMediaById(String mediaId, Map<String, String> credential) throws Exception;
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
	 * @param thumbnail url
	 * @param credential user name and password from session
	 * @return return thumbnail data(img/png)
	 */
	ResponseEntity<byte[]> getThumbnail(String thumbnailURL, Map<String, String> credential) throws Exception;
	/**
	 * Restore media previous revision as current 
	 * @param mediaId media id
	 * @param mediaItemFileId media previous file id
	 * @param credential user name, password or tokens from session
	 * 
	 * @return return media info map
	 */
	Map<String, Object> restoreMediaRevision(String mediaId, String mediaItemFileId, Map<String,String> credential) throws Exception;
	/**
	 * create new media
	 * @param credential user name and password from session
	 * @return
	 * @throws Exception
	 * @author jjzhu
	 */
	Map<String, Object> createMedia(Map<String , String> parameter , Map<String, String> credential) throws Exception;
}
