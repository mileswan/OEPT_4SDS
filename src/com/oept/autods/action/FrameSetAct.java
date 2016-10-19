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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oept.autods.api.service.CmAPIAuth;
import com.oept.autods.api.service.CmAPIFrameSet;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/07
 * Description: Frameset management when navigate to Marketing network page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/frameset")
public class FrameSetAct {
	
	@Qualifier("frameSetAPIService")
    @Autowired
    private CmAPIFrameSet frameSetAPIService;
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	private static final Log logger = LogFactory.getLog(FrameSetAct.class);
	
	@RequestMapping(value="/list.do")
	public String navToFrameSetList(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				frameSetAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				model.addAttribute("frameSetList", frameSetAPIService.getFrameSetList());
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "frameSetList";		
	}
	
	@RequestMapping(value="/list.do", params = "json")
	@ResponseBody
	public String getFrameSetList(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String queryParam = request.getParameter("queryParam");
				if(queryParam == null || "".equals(queryParam)){
					frameSetAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				}else{
					frameSetAPIService.generateList(credential, queryParam);
				}
				
				ObjectMapper om = new ObjectMapper();
				String responseStr = om.writeValueAsString(frameSetAPIService.getFrameSetList());
				
				return responseStr;	
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}	
	}

	@RequestMapping(value="/details.do")
	public String navToFrameSetDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){

				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String frameSetId = request.getParameter("frameSetId");				
				Map<String,Object> frameSetDetail = frameSetAPIService.findFrameSetById(frameSetId, null, credential);
				
				model.addAttribute("frameSetDetail", frameSetDetail);
				
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}	
		return "frameSetDetails";
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateFrameSet(HttpServletRequest request, HttpSession session) throws Exception{	
		return null;
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public String deleteFrameSetById(HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			String frameSetIds = request.getParameter("frameSetIds");
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());			
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String frameSetId[] = frameSetIds.split(",");
				for(int i=0;i<frameSetId.length;i++){
					frameSetAPIService.deleteFrameSetById(frameSetId[i], credential);
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
	public String createFrameSet(HttpServletRequest request, HttpSession session) throws Exception{
		
		return null;
	}
}
