package com.oept.autods.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.oept.autods.api.service.CmAPIPlayer;
import com.oept.autods.api.service.CmAPIStorage;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/07
 * Description: Player management when navigate to Marketing network page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/player")
public class PlayerAct {
	
	@Qualifier("playerAPIService")
    @Autowired
    private CmAPIPlayer playerAPIService;
	@Qualifier("storageAPIService")
    @Autowired
    private CmAPIStorage storageAPIService;
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	private static final Log logger = LogFactory.getLog(PlayerAct.class);
	
	@RequestMapping(value="/list.do")
	public String navToPlayerList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				playerAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				model.addAttribute("playerList", playerAPIService.getPlayerList());
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "playerList";		
	}
	
	@RequestMapping(value="/list.do", params = "json")
	@ResponseBody
	public String getPlayerList(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String queryParam = request.getParameter("queryParam");
				if(queryParam == null || "".equals(queryParam)){
					playerAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				}else{
					playerAPIService.generateList(credential, queryParam);
				}			
				
				ObjectMapper om = new ObjectMapper();
				String responseStr = om.writeValueAsString(playerAPIService.getPlayerList());
				
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
	public String navToPlayerDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){

				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String playerId = request.getParameter("playerId");				
				Map<String,Object> playerDetail = playerAPIService.findPlayerById(playerId, null, credential);
				
				if((boolean) playerDetail.get("enabled")){
					model.addAttribute("enable_true", "active");
				}else{
					model.addAttribute("enable_false", "active");
				}
				if((boolean) playerDetail.get("previewPlayer")){
					model.addAttribute("previewPlayer_true", "active");
				}else{
					model.addAttribute("previewPlayer_false", "active");
				}
				
				model.addAttribute("playerDetail", playerDetail);
				
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}	
		return "playerDetails";
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updatePlayer(HttpServletRequest request, HttpSession session) throws Exception{	
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				
				ObjectMapper om = new ObjectMapper();
				String playerId = request.getParameter("playerId");
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());

				Map<String, Object> requestParam = new HashMap<String, Object>();
				Map<String, Object> responseBody = new HashMap<String, Object>();

				String description = request.getParameter("description");
				String channelIds = request.getParameter("channelIds");
				String name = request.getParameter("name");
				
				if(!("".equals(channelIds)) && channelIds != null){
					List<Map<String, Object>> playerDisplaysList = new ArrayList<Map<String, Object>>();
					
					String channelId[] = channelIds.split(",");
					for(int i=0;i<channelId.length;i++){
						Map<String,Object> channel = new HashMap<String, Object>();
						Map<String, Object> playerDisplay = new HashMap<String, Object>();
						channel.put("id", Integer.parseInt(channelId[i]));
						playerDisplay.put("screenCounter", 1);//this is required!!!
						playerDisplay.put("channel", channel);
						playerDisplaysList.add(playerDisplay);
					}
					
					requestParam.put("playerDisplays", playerDisplaysList);
				}
				if(request.getParameter("numberOfDisplays")!=null){
					int numberOfDisplays = Integer.parseInt(request.getParameter("numberOfDisplays"));
					requestParam.put("numberOfDisplays", numberOfDisplays);
				}
				if(request.getParameter("enabled")!=null){
					Boolean enabled = Boolean.parseBoolean(request.getParameter("enabled"));
					requestParam.put("enabled", enabled);
				}							
				
				if(!("".equals(description)) && description != null){
					requestParam.put("description", description);
				}
				if(!("".equals(name)) && name != null){
					requestParam.put("name", name);
				}
				
				responseBody = playerAPIService.updatePlayer(playerId, requestParam, credential);				
				
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
	public String deletePlayerById(HttpServletRequest request, HttpSession session) throws Exception{
		
		try {
			String playerIds = request.getParameter("playerIds");
			Map<String,String> credential = new HashMap<String, String>();
			credential.put("username", session.getAttribute("username").toString());
			credential.put("password", session.getAttribute("password").toString());			
			
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String playerId[] = playerIds.split(",");
				for(int i=0;i<playerId.length;i++){
					playerAPIService.deletePlayerById(playerId[i], credential);
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
	public String createPlayer(HttpServletRequest request, HttpSession session) throws Exception{
		
		return null;
	}
	
	@RequestMapping(value="/generate.do", method = RequestMethod.POST)
	@ResponseBody
	public String generatePlan(HttpServletRequest request, HttpSession session) throws Exception{
	
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				String playerId = request.getParameter("playerId");
				ObjectMapper om = new ObjectMapper();
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());	
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				Map<String,Object> requestParam = new HashMap<String, Object>();
				List<Integer> idList = new ArrayList<Integer>();
				idList.add(Integer.parseInt(playerId));
				requestParam.put("ids", idList);		
				
				Map<String,Object> uuidInfo = storageAPIService.postStoreValue(requestParam, credential);
				String uuid = uuidInfo.get("value").toString();
				Map<String,Object> response = playerAPIService.generatePlan(uuid, credential);
				
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
