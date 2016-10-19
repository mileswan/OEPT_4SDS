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
 * Description: User's roles management when navigate to system page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/role")
public class RoleAct {
	
	@Qualifier("roleAPIService")
	@Autowired
	private CmAPIRole roleAPIService;
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	private static final Log logger = LogFactory.getLog(RoleAct.class);
	
	@RequestMapping(value="/list.do")
	public String navToRolesList(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				roleAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				model.addAttribute("rolesList", roleAPIService.getRolesList());
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "roleList";		
	}

	@RequestMapping(value="/details.do")
	public String navToRoleDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				String roleId = request.getParameter("roleId");
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				model.addAttribute("roleDetails", roleAPIService.findRoleById(roleId, "", credential));
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "roleDetails";		
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
