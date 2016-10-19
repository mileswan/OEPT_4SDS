package com.oept.autods.action;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.oept.autods.api.service.CmAPIAuth;
import com.oept.autods.api.service.CmAPIUser;
/**
 * @author mwan
 * Version: 2.0
 * Date: 2015/08/17
 * Description: Personal Settings actions.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/personal")
public class PersonalSettingsAct {
	
	private static final Log logger = LogFactory.getLog(PersonalSettingsAct.class);
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	@Qualifier("userAPIService")
	@Autowired
	private CmAPIUser userAPIService;
	//User settings view
	@RequestMapping(value="/settings.do")
	public String settingsView(Model model, HttpServletRequest request, HttpSession session) throws Exception{	
		
		try{	
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
			
				Map<String, Object> userInfo = userAPIService.getCurrentUser(credential);
				model.addAttribute("username", session.getAttribute("username"));
				model.addAttribute("lastname", userInfo.get("lastName"));
				model.addAttribute("firstname", userInfo.get("firstName"));
				model.addAttribute("emailAddress", userInfo.get("emailAddress"));
				if((boolean) userInfo.get("receiveEmailAlerts")){
					model.addAttribute("receiveEmailAlerts_true", "active");
				}else{
					model.addAttribute("receiveEmailAlerts_false", "active");
				}
				if((boolean) userInfo.get("isAutoMediaApprover")){
					model.addAttribute("isAutoMediaApprover_true", "active");
				}else{
					model.addAttribute("isAutoMediaApprover_false", "active");
				}
				
				return "userSettings";
			}else{
				return "redirect:/auth/logout.do";
			}
			
		}catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			throw e;
		}
			
	}
	//Change password view
	@RequestMapping(value="/password.do")
	public String changePasswordView(Model model, HttpServletRequest request, HttpSession session){	

		model.addAttribute("username", session.getAttribute("username"));
		return "changePassword";

	}
	//Change password action
	@RequestMapping(value="/changePassword.do")
	@ResponseBody
	public String changePassword(Model model, HttpServletRequest request, HttpSession session) throws Exception{	

		try{
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				String userId = session.getAttribute("userId").toString();

				String oldPassword = request.getParameter("oldPassword");
				String newPassword = request.getParameter("newPassword");
				String confirmPassword = request.getParameter("confirmPassword");

				if("".equals(oldPassword) || "".equals(newPassword) || "".equals(confirmPassword)){
					return "inputErr";
				}else if( !(oldPassword.equals(session.getAttribute("password").toString())) ){
					return "oldPasswordErr";
				}else if( !(confirmPassword.equals(newPassword)) ){
					return "newPasswordErr";
				}
				Map<String, Object> requestBody = new HashMap<String, Object>();
				requestBody.put("id", userId);
				requestBody.put("canChangePassword", true);
				requestBody.put("oldPassword", oldPassword);
				requestBody.put("newPassword", newPassword);
				
				userAPIService.updateCurrentUser(userId, requestBody, credential);
				return "success";
			}else{
				return "redirect:/auth/logout.do";
			}
		
		}catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	//Update user settings action
	@RequestMapping(value="/updateSettings.do")
	@ResponseBody
	public String updateSettings(Model model, HttpServletRequest request, HttpSession session) throws Exception{	

		try{
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				String userId = session.getAttribute("userId").toString();
				
				String lastname = request.getParameter("lastname");
				String firstname = request.getParameter("firstname");
				String emailAddress = request.getParameter("emailAddress");
				Boolean isAutoMediaApprover = Boolean.parseBoolean(request.getParameter("isAutoMediaApprover"));
				Boolean receiveEmailAlerts = Boolean.parseBoolean(request.getParameter("receiveEmailAlerts"));
				
				Map<String, Object> requestBody = new HashMap<String, Object>();
				requestBody.put("id", userId);
				requestBody.put("lastName", lastname);
				requestBody.put("firstName", firstname);
				requestBody.put("emailAddress", emailAddress);
				requestBody.put("isAutoMediaApprover", isAutoMediaApprover);
				requestBody.put("receiveEmailAlerts", receiveEmailAlerts);
				
				userAPIService.updateCurrentUser(userId, requestBody, credential);
				return "success";
			}else{
				return "redirect:/auth/logout.do";
			}					
		}catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			throw e;
		}

	}
}
