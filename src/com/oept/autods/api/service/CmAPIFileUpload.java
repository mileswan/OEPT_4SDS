/**
 * 
 */
package com.oept.autods.api.service;

import java.util.Map;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/06/30
 * Description: Invoke Content Manager API for file upload.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public interface CmAPIFileUpload {
	/**
	 * Begin a file upload and obtain an upload id.
	 * @param request parameters
	 * 
	 * @return return responses from CM api��
	 * @throws Exception 
	 */
	Map<String, Object> initUpload(Map<String, Object> request, Map<String,String> credential) throws Exception;
	/**
	 * Send one part of a multipart file upload
	 * @param request parameters
	 * 
	 * @return return responses from CM api����
	 */
	void uploadFilePart(Map<String, Object> request, Map<String,String> credential) throws Exception;
	/**
	 * Upload media data. Once all data is transfered, the server will automatically call 'complete' service
	 * @param request parameters
	 * 
	 * @return return responses from CM api��
	 */
	void uploadSingleFile(Map<String, Object> request, Map<String,String> credential) throws Exception;
	/**
	 * Indicates all parts of the file have been sent via fileupload/part
	 * @param request parameters
	 * 
	 * @return return responses from CM api��
	 */
	void uploadFinished(Map<String, Object> request, Map<String,String> credential) throws Exception;
}
