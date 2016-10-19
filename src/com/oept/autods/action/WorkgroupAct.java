package com.oept.autods.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.oept.autods.api.service.CmAPIAuth;
import com.oept.autods.api.service.CmAPIRole;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/06
 * Description: Workgroup management when navigate to system page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/workgroup")
public class WorkgroupAct {
	
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	private static final Log logger = LogFactory.getLog(WorkgroupAct.class);
	
	@RequestMapping(value="/list.do")
	public String navToWorkgroupList(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		return "workgroupList";		
	}

	@RequestMapping(value="/details.do")
	public String navToWorkgroupDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		
		return "workgroupDetails";		
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateRole(HttpServletRequest request, HttpSession session) throws Exception{	
		return null;
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRoleById(HttpServletRequest request, HttpSession session) throws Exception{
		
		return null;
	}
	
	@RequestMapping(value="/create.do", method = RequestMethod.POST)
	@ResponseBody
	public String createRole(HttpServletRequest request, HttpSession session) throws Exception{
		
		return null;
	}
}
