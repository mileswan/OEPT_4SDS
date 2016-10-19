package com.oept.autods.common.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/05/26
 * Description: Methods used to invoke RESTful Webservice.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public class WebServiceManager {
	
	private RestTemplate _restTemplate;
	private String _loginToken;
	private String _apiToken;
	private String _responseStr;
	private Map<String, Object> _resopnseMap;
	//Constructor
	public WebServiceManager() {
		super();
		this._restTemplate = new RestTemplate();
		this._apiToken = "";
		this._loginToken = "";
		this._responseStr = "";
		this._resopnseMap = null;
	}
	
	public String getToken()
	{
		return this._loginToken;
	}

	public String getAPIToken()
	{
		return this._apiToken;
	}
	public String getResponseStr()
	{
		return this._responseStr;
	}
	
	public void setToken(String token)
	{
		this._loginToken = token;
	}
	
	public void setAPIToken(String apiToken)
	{
		this._apiToken = apiToken;
	}
	
	public Map<String, Object> getResponseMap()
	{
		return this._resopnseMap;
	}
	//Invoke RESTful POST Webservice
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestPost(String strURI, Object params) throws Exception
	{  
		HttpHeaders headers =new HttpHeaders();		
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> request=new HttpEntity<Object>(params, headers);
	    
		Map<String, Object> response = _restTemplate.postForObject(strURI, request, Map.class);
		if(response.get("token") != null)
		{
			_loginToken = response.get("token").toString();
			_apiToken = response.get("apiToken").toString();
		}	
		this._responseStr = response.toString();
		return response;
	}
	//Invoke RESTful POST Webservice to return the raw string
	@SuppressWarnings("unchecked")
	public String sendRESTRequestPostString(String strURI, Object params) throws Exception
	{  
		HttpHeaders headers =new HttpHeaders();		
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> request=new HttpEntity<Object>(params, headers);
	    ObjectMapper om = new ObjectMapper();
	    
	    String response = _restTemplate.postForObject(strURI, request, String.class);
		Map<String, Object> responseMap = om.readValue(response, Map.class);
		if(responseMap.get("token") != null)
		{
			_loginToken = responseMap.get("token").toString();
			_apiToken = responseMap.get("apiToken").toString();
		}
		this._resopnseMap = responseMap;
		return response;
	}
	//Invoke RESTful GET Webservice which needs token or not
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestGet(String strURI, Boolean requireToken) throws Exception
	{
		if(requireToken == true)
		{
			HttpHeaders headers =new HttpHeaders();
			headers.set("token", _loginToken);
			ResponseEntity<String> responseEntity = _restTemplate.exchange(strURI,  
				      HttpMethod.GET,  
				      new HttpEntity<byte[]>(headers),  
				      String.class);
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> response = om.readValue(responseEntity.getBody(), Map.class);
			return response;
		}
		else
		{
			Map<String, Object> response = _restTemplate.getForObject(strURI, Map.class);
			return response;
		}
	}
	//Invoke RESTful GET Webservice which needs special return type
	public <T> Object sendRESTRequestGet(String strURI, Class<T> resType) throws Exception
	{
		T response = _restTemplate.getForObject(strURI, resType);
		return response;
	}
	//Invoke RESTful GET Webservice which needs apiToken without parameter
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestGetwithAPIToken(String strURI) throws Exception
	{
		HttpHeaders headers =new HttpHeaders();
		headers.set("apiToken", _apiToken);
		URI uri = new URI(strURI);
		//Use URI parameter to prevent to encode url twice
		ResponseEntity<String> responseEntity = _restTemplate.exchange(uri,  
				HttpMethod.GET,  
				new HttpEntity<byte[]>(headers),  
				String.class);
		
		if(responseEntity.getBody() != null){
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> response = om.readValue(responseEntity.getBody(), Map.class);
			this._responseStr = responseEntity.getBody();
			
			return response;
		}else{
			return null;
		}
		
	}
	//Invoke RESTful GET Webservice which needs apiToken and parameters
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestGetwithAPIToken(String strURI, Object params) throws Exception
	{
		HttpHeaders headers =new HttpHeaders();
		ObjectMapper om = new ObjectMapper();
		
		headers.set("apiToken", _apiToken);
		ResponseEntity<String> responseEntity = _restTemplate.exchange(strURI,  
				HttpMethod.GET,  
				new HttpEntity<byte[]>(headers),  
				String.class, params);
		
		Map<String, Object> response = om.readValue(responseEntity.getBody(), Map.class);
		this._responseStr = responseEntity.getBody();
		
		return response;
	}
	//Invoke RESTful POST Webservice which needs apiToken without parameters
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestPostwithAPIToken(String strURI) throws Exception
	{  
		HttpHeaders headers =new HttpHeaders();
		headers.set("apiToken", _apiToken);
	    HttpEntity<Object> request=new HttpEntity<Object>(headers);
	    
		Map<String, Object> response = _restTemplate.postForObject(strURI, request, Map.class);
	
		if(response != null){
			this._responseStr = response.toString();
		}
		
		return response;
	}
	//Invoke RESTful POST Webservice which needs apiToken and parameters
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestPostwithAPIToken(String strURI, Object params) throws Exception
	{  
		HttpHeaders headers =new HttpHeaders();
		headers.set("apiToken", _apiToken);
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> request=new HttpEntity<Object>(params, headers);
	    
		Map<String, Object> response = _restTemplate.postForObject(strURI, request, Map.class);
	
		if(response != null){
			this._responseStr = response.toString();
		}
		
		return response;
	}
	//Invoke RESTful PUT Webservice which needs apiToken without parameter
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestPutwithAPIToken(String strURI) throws Exception
	{
		HttpHeaders headers =new HttpHeaders();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> response = new HashMap<String, Object>();
		
		headers.set("apiToken", _apiToken);
		ResponseEntity<String> responseEntity = _restTemplate.exchange(strURI,  
				HttpMethod.PUT,
				new HttpEntity<byte[]>(headers),  
				String.class);
		
		if(responseEntity != null && responseEntity.getBody() != null){
			response = om.readValue(responseEntity.getBody(), Map.class);
		}
		return response;
	}
	//Invoke RESTful PUT Webservice which needs apiToken and parameters
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestPutwithAPIToken(String strURI, Object params) throws Exception
	{
		HttpHeaders headers =new HttpHeaders();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> response = new HashMap<String, Object>();
		
		headers.set("apiToken", _apiToken);
		ResponseEntity<String> responseEntity = _restTemplate.exchange(strURI,  
				HttpMethod.PUT,
				new HttpEntity<Object>(params,headers),  
				String.class);
		
		if(responseEntity != null && responseEntity.getBody() != null){
			response = om.readValue(responseEntity.getBody(), Map.class);
		}
		
		return response;
	}
	//Invoke RESTful DELETE Webservice which needs apiToken without parameter
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRESTRequestDeletewithAPIToken(String strURI) throws Exception
	{
		HttpHeaders headers =new HttpHeaders();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> response = new HashMap<String, Object>();
		
		headers.set("apiToken", _apiToken);
		ResponseEntity<String> responseEntity = _restTemplate.exchange(strURI,  
				HttpMethod.DELETE,
				new HttpEntity<byte[]>(headers),  
				String.class);
		
		if(responseEntity != null && responseEntity.getBody() != null){
			response = om.readValue(responseEntity.getBody(), Map.class);
		}
		return response;
	}
	//Invoke GET method to get web resource which needs token
	public ResponseEntity<byte[]> getWebResource(String strURI) throws Exception
	{
		HttpHeaders headers =new HttpHeaders();
		
		headers.set("Token", _loginToken);
		ResponseEntity<byte[]> responseEntity = _restTemplate.exchange(strURI,  
				HttpMethod.GET,  
				new HttpEntity<byte[]>(headers),  
				byte[].class);
		
		return responseEntity;
	}
}
