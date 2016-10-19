package com.oept.autods.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/05/25
 * Description: Methods used to manage property file.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public class PropFileManager {
	private	InputStream _fis; 
	private Properties _properties;
	private static final Log logger = LogFactory.getLog(PropFileManager.class);
	//Construct method
	public PropFileManager(String fileName)
	{
		try {
			_properties = new Properties();
			_fis = this.getClass().getClassLoader().getResourceAsStream(fileName);		
			_properties.load(_fis);
			_fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e);
		}
	}
	//Get property value
	public String getProperty(String propertyName){
		return _properties.getProperty(propertyName);
	}
}
