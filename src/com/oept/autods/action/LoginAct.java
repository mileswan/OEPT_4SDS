package com.oept.autods.action;

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
import com.oept.autods.dao.userDao;
import com.oept.autods.model.User;
/**
 * @author mwan
 * Version: 2.0
 * Date: 2015/08/17
 * Description: User login, validation and logout actions.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/auth")
public class LoginAct {
	
	private static final Log logger = LogFactory.getLog(LoginAct.class);
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	//User authentication when login
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String userAuthenticate(Model model, HttpServletRequest request, HttpSession session){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		model.addAttribute("username", username);		
		
		try {
			Map<String, String> credential = new HashMap<String, String>();
			credential.put("username", username);
			credential.put("password", password);
			
			Map<String, Object> response = authAPIService.login(credential);
			
			String loginStatus = response.get("status").toString();
			String loginSuccess = new String("login.success");
			if(loginStatus.equals(loginSuccess))
			{
				session.setAttribute("username", username);
				session.setAttribute("userId", response.get("userId"));
				session.setAttribute("password", password);
				session.setAttribute("token", authAPIService.getToken());
				session.setAttribute("apiToken", authAPIService.getAPIToken());
				return "homePage";
			}			
			else
				return "authenticationFail";
		} catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			model.addAttribute("exception", e.toString());
			return "exception";
		}
	}
	//User authentication usually invoked by JS when login
	@RequestMapping(value="/login.do", params = "json")
	@ResponseBody
	public String userAuthenticate(HttpServletRequest request, HttpSession session) throws Exception{
		String username = request.getParameter("username");
		String password = request.getParameter("password");	
		Map<String, String> response = new HashMap<String, String>();

		try {
			Map<String, String> credential = new HashMap<String, String>();
			credential.put("username", username);
			credential.put("password", password);
			
			response = authAPIService.loginFromJs(credential);

			String loginStatus = response.get("loginStatus");
			String loginSuccess = new String("login.success");
			if(loginStatus.equals(loginSuccess))
			{
				session.setAttribute("username", username);
				session.setAttribute("userId", response.get("userId"));
				session.setAttribute("password", password);
				session.setAttribute("token", authAPIService.getToken());
				session.setAttribute("apiToken", authAPIService.getAPIToken());
				return response.get("responseStr");
			}			
			else
				return response.get("responseStr");
		} catch (Exception e) {
			// Exception handle
			logger.info(e.getMessage());
			throw e;
		}
	}

	//Display home page dashboard
	@RequestMapping(value="/dashboard.do")
	public String navToDashboad(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response)
	{

		model.addAttribute("username", session.getAttribute("username"));
		return "homePage";
	}
	
	//User authentication when login
	@RequestMapping(value="/logout.do")
	public String userLogout(Model model, HttpServletRequest request, HttpSession session){
		try {
			authAPIService.logout(session.getAttribute("token").toString());
			
			session.removeAttribute("username");
			session.removeAttribute("password");
			session.removeAttribute("token");
			session.removeAttribute("apiToken");

			return "redirect:/";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			model.addAttribute("exception", e.toString());
			return "exception";
		}
	}
	
	@Autowired
	userDao userdao;
	//Display home page dashboard
	@RequestMapping(value="/test.do")
	public String test(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response)
	{
		User user = new User();
		user.set_NAME("test1");
		user.set_PASSWORD("1234");
		userdao.insertUser(user);
		
		return null;
	}
	
}
