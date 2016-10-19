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
 * Date: 2015/09/02
 * Description: Channel management when navigate to Marketing network page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/channel")
public class ChannelAct {
	
	@Qualifier("channelAPIService")
    @Autowired
    private CmAPIChannel channelAPIService;
	@Qualifier("playerAPIService")
    @Autowired
    private CmAPIPlayer playerAPIService;
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	private static final Log logger = LogFactory.getLog(ChannelAct.class);
	
	@RequestMapping(value="/list.do")
	public String navToChannelList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				channelAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				model.addAttribute("channelList", channelAPIService.getChannelList());
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "channelList";		
	}

	@RequestMapping(value="/details.do")
	public String navToChannelDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){

				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String channelId = request.getParameter("channelId");				
				Map<String,Object> channelDetail = channelAPIService.findChannelById(channelId, null, credential);
											
				model.addAttribute("channelId", channelId);
				model.addAttribute("channelDetail", channelDetail);
				
				if((boolean) channelDetail.get("muteAudioFromVisual")){
					model.addAttribute("muteAudioFromVisual_true", "active");
				}else{
					model.addAttribute("muteAudioFromVisual_false", "active");
				}
				
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "channelDetails";		
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateChannel(HttpServletRequest request, HttpSession session) throws Exception{	
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				
				ObjectMapper om = new ObjectMapper();
				String channelId = request.getParameter("channelId");
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());

				Map<String, Object> requestParam = new HashMap<String, Object>();
				Map<String, Object> responseBody = new HashMap<String, Object>();

				String description = request.getParameter("description");
				String name = request.getParameter("name");
				String channelType = request.getParameter("channelType");
				Boolean muteAudioFromVisual = Boolean.parseBoolean(request.getParameter("muteAudioFromVisual"));			
				
				requestParam.put("description", description);
				if(!("".equals(name)) && name != null){
					requestParam.put("name", name);
				}
				requestParam.put("type",  channelType);
				requestParam.put("muteAudioFromVisual", muteAudioFromVisual);
				
				responseBody = channelAPIService.updateChannel(channelId, requestParam, credential);				
				
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
	public String deleteChannelById(HttpServletRequest request, HttpSession session) throws Exception{
		
		String channelIds = request.getParameter("channelIds");
		
		try {
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());			
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String channelId[] = channelIds.split(",");
				for(int i=0;i<channelId.length;i++){
					channelAPIService.deleteChannelById(channelId[i], credential);
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
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String framesetId = request.getParameter("framesetId");
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());	
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				ObjectMapper om = new ObjectMapper();
				
				Map<String,Object> requestParam = new HashMap<String, Object>();
				Map<String,Object> frameSet = new HashMap<String, Object>();
				requestParam.put("name", name);
				requestParam.put("description", description);
				requestParam.put("type", "AUDIOVISUAL");
				requestParam.put("playDedicatedAudioTrack", false);
				frameSet.put("id", framesetId);
				requestParam.put("frameset", frameSet);
				
				Map<String,Object> response = channelAPIService.createChannel(requestParam, credential);
				
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
	
	@RequestMapping(value="/publish.do")
	public String publishToPlayer(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				channelAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				model.addAttribute("channelList", channelAPIService.getChannelList());
				
				playerAPIService.generateList(credential, "?limit=0&offset=0&sort=name&fields=id,name,type,playerDisplays,lastModified,active,uuid");
				model.addAttribute("playerList", playerAPIService.getPlayerList());
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "publishList";
	}

}
