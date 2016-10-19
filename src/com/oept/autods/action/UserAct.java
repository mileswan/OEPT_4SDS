package com.oept.autods.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.oept.autods.api.service.CmAPIRole;
import com.oept.autods.api.service.CmAPIUser;
import com.oept.autods.dao.userDao;
import com.oept.autods.service.UserService;
import com.sun.media.jfxmedia.Media;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/05
 * Description: User management when navigate to system page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/user")
public class UserAct {
	
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	@Qualifier("userAPIService")
	@Autowired
	private CmAPIUser userAPIService;
	@Qualifier("roleAPIService")
	@Autowired
	private CmAPIRole roleAPIService;
	private static final Log logger = LogFactory.getLog(UserAct.class);
	
	
	//注解
	@Qualifier("UserService")
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/list.do")
	public String navToUserList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				userAPIService.generateList(credential, "?limit=0&offset=0&sort=username");
				
				model.addAttribute("userList", userAPIService.getUserList());
				
				
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
		
		return "userList";		
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/details.do")
	public String navToUserDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){

				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String userId = request.getParameter("userId");		
				Map<String,Object> userDetails = userAPIService.findUserById(userId, credential);
											
				model.addAttribute("userId", userId);
				model.addAttribute("userDetails", userDetails);
				
				/*存入session,方便updateChannellist方法调用*/
				session.setAttribute("receiveEmailAlerts",userDetails.get("receiveEmailAlerts"));
				session.setAttribute("roles", userDetails.get("roles"));
				
				if((boolean) userDetails.get("enabled")){
					model.addAttribute("userActive_true", "active");
				}else{
					model.addAttribute("userActive_false", "active");
				}
				if((boolean) userDetails.get("canChangePassword")){
					model.addAttribute("canChangePassword_true", "active");
				}else{
					model.addAttribute("canChangePassword_false", "active");
				}
				if((boolean) userDetails.get("isAutoMediaApprover")){
					model.addAttribute("isAutoMediaApprover_true", "active");
				}else{
					model.addAttribute("isAutoMediaApprover_false", "active");
				}
				if((boolean) userDetails.get("receiveEmailAlerts")){
					model.addAttribute("receiveEmailAlerts_true", "active");
				}else{
					model.addAttribute("receiveEmailAlerts_false", "active");
				}
				if((boolean) userDetails.get("receiveApprovalEmails")){
					model.addAttribute("receiveApprovalEmails_true", "active");
				}else{
					model.addAttribute("receiveApprovalEmails_false", "active");
				}
				
				roleAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				
				List<Map<String, Object>> rolesList = (List<Map<String, Object>>) roleAPIService.getRolesList();
				List<Map<String, Object>> userRolesList = (List<Map<String, Object>>) userDetails.get("roles");
				Iterator<Map<String, Object>> iter = rolesList.iterator();
				Iterator<Map<String, Object>> iterUser = userRolesList.iterator();
				Map<String,Object> role = new HashMap<String, Object>();
				Map<String,Object> userRole = new HashMap<String, Object>();
							
				while(iter.hasNext()){
					role = iter.next();
					iterUser = userRolesList.iterator();
					while(iterUser.hasNext())  
			        {  
						userRole = iterUser.next();
						if((int)role.get("id") == (int)userRole.get("id")){
							role.put("checked", true);
						}		              
			        }
				}
				
				model.addAttribute("rolesList", rolesList);
				
				model.addAttribute("allPosition",userService.selectAllPosition());
				
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "userDetails";		
	}
	
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateChannellist(HttpServletRequest request, HttpSession session,Model model ) throws Exception{	
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				
				ObjectMapper om = new ObjectMapper();
				String userId = request.getParameter("userId");
				
				
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());

				Map<String, Object> requestParam = new HashMap<String, Object>();
				Map<String, Object> responseBody = new HashMap<String, Object>();
//				List<Map<String, Object>> rolesList = new ArrayList<Map<String, Object>>();

				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String emailaddress = request.getParameter("emailaddress");
				String lastname = request.getParameter("lastname");
				String firstname = request.getParameter("firstname");
				
				String receiveEmailAlerts = request.getParameter("receiveEmailAlerts");
				String roles = request.getParameter("roles");
				
				String position = request.getParameter("position");
				
				
//				/*测试*/
//				System.out.println("userId:"+userId);
//				System.out.println("username:"+username);
//				System.out.println("password:"+password);
//				System.out.println("emailaddress:"+emailaddress);
//				System.out.println("firstname:"+firstname);
//				System.out.println("receiveEmailAlerts:"+receiveEmailAlerts);
//				System.out.println("roles:"+roles);
//				System.out.println("position:"+position);
				
//				String roleIds = request.getParameter("roleIds");
//				Boolean enabled = Boolean.parseBoolean(request.getParameter("enabled"));
//				Boolean receiveApprovalEmails = Boolean.parseBoolean(request.getParameter("receiveApprovalEmails"));
//				Boolean receiveEmailAlerts = Boolean.parseBoolean(request.getParameter("receiveEmailAlerts"));
//				Boolean canChangePassword = Boolean.parseBoolean(request.getParameter("canChangePassword"));
//				Boolean isAutoMediaApprover = Boolean.parseBoolean(request.getParameter("isAutoMediaApprover"));
				
				requestParam.put("username", username);
//				requestParam.put("password",  password);
				requestParam.put("emailaddress", emailaddress);
				requestParam.put("lastname",  lastname);
				requestParam.put("firstname", firstname);
				
				//session中取必须值
				requestParam.put("receiveEmailAlerts", session.getAttribute("receiveEmailAlerts"));
				requestParam.put("roles", session.getAttribute("roles"));
				
				
//				requestParam.put("enabled", enabled);
//				requestParam.put("receiveApprovalEmails",  receiveApprovalEmails);
//				requestParam.put("receiveEmailAlerts", receiveEmailAlerts);
//				requestParam.put("canChangePassword",  canChangePassword);
//				requestParam.put("isAutoMediaApprover", isAutoMediaApprover);
				
//				String roleId[] = roleIds.split(",");
//				for(int i=0;i<roleId.length;i++){
//					Map<String,Object> role = new HashMap<String, Object>();
//					role.put("id", Integer.parseInt(roleId[i]));
//					rolesList.add(role);
//				}
//				requestParam.put("roles", rolesList);
				
//				String test = om.writeValueAsString(requestParam);
				
				responseBody = userAPIService.updateUserById(userId, requestParam, credential);
				
				//调用更新职位方法
				int i = userService.updatePosition(position,userId);
				System.out.println("mysql:"+i);
				
				String responseStr = om.writeValueAsString(responseBody);	
				
				return responseStr;
			}else{
				return "redirect:/auth/logout.do";
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public String deleteUserById(HttpServletRequest request, HttpSession session) throws Exception{
		
		String userIds = request.getParameter("userIds");
		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());			
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String userId[] = userIds.split(",");
				for(int i=0;i<userId.length;i++){
					userAPIService.deleteUserById(userId[i], credential);
				}
				
				return "delete.success";
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/create.do", method = RequestMethod.POST)
	@ResponseBody
	public String createChannel(HttpServletRequest request, HttpSession session) throws Exception{		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String lastname = request.getParameter("lastname");
				String firstname = request.getParameter("firstname");
				String emailAddress = request.getParameter("emailAddress");
				String roleIds = request.getParameter("roleIds");
				
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());	
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				ObjectMapper om = new ObjectMapper();
				
				Map<String,Object> requestParam = new HashMap<String, Object>();
				List<Map<String, Object>> rolesList = new ArrayList<Map<String, Object>>();
				
				requestParam.put("username", username);
				requestParam.put("password", password);
				requestParam.put("oldPassword", password);
				requestParam.put("lastname", lastname);
				requestParam.put("firstname", firstname);
				requestParam.put("emailAddress", emailAddress);
				requestParam.put("receiveEmailAlerts", true);
				
				String roleId[] = roleIds.split(",");
				for(int i=0;i<roleId.length;i++){
					Map<String,Object> role = new HashMap<String, Object>();
					role.put("id", Integer.parseInt(roleId[i]));
					rolesList.add(role);
				}
				requestParam.put("roles", rolesList);
				
				Map<String,Object> response = userAPIService.createUser(requestParam, credential);
				
				
				
				String responseStr = om.writeValueAsString(response);
				
				return responseStr;
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
}
