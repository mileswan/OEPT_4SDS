package com.oept.autods.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oept.autods.api.service.CmAPIAuth;
import com.oept.autods.api.service.CmAPIChannel;
import com.oept.autods.api.service.CmAPIPlayer;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/10
 * Description: Location management when navigate to Marketing network page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/location")
public class LocationAct {
	
	@Qualifier("playerAPIService")
    @Autowired
    private CmAPIPlayer playerAPIService;
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	private static final Log logger = LogFactory.getLog(LocationAct.class);
	
	@RequestMapping(value="/list.do")
	public String navToLocationList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		return "locationList";		
	}

	@RequestMapping(value="/details.do")
	public String navToLocationDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		return "locationDetails";		
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateLocation(HttpServletRequest request, HttpSession session) throws Exception{	
		return null;
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public String deleteLocation(HttpServletRequest request, HttpSession session) throws Exception{
		
		return null;
	}
	
	@RequestMapping(value="/create.do", method = RequestMethod.POST)
	@ResponseBody
	public String createLocation(HttpServletRequest request, HttpSession session) throws Exception{
		
		return null;
	}
}
