package com.oept.autods.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oept.autods.api.service.CmAPIAuth;
import com.oept.autods.api.service.CmAPIMedia;
import com.oept.autods.api.service.CmAPIPlaylist;
import com.oept.autods.api.service.impl.CmAPIMediaImpl;
import com.oept.autods.common.util.StringUtils;

/**
 * @author mwan
 * Version: 2.0
 * Date: 2015/08/27
 * Description: Playlist management when navigate to Marketing media page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/playlist")
public class PlaylistAct {
	
	@Qualifier("playlistAPIService")
	@Autowired
	private CmAPIPlaylist playlistAPIService;
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	private static final Log logger = LogFactory.getLog(PlaylistAct.class);
	
	@RequestMapping(value="/list.do")
	public String navToPlayList(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				playlistAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				model.addAttribute("playlistList", playlistAPIService.getPlaylistList());
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "playList";		
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public String deletePlaylistById(HttpServletRequest request, HttpSession session) throws Exception{
		
		String playlistIds = request.getParameter("playlistIds");
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String playlistId[] = playlistIds.split(",");
				for(int i=0;i<playlistId.length;i++){
					playlistAPIService.deletePlaylistById(playlistId[i], credential);
				}
				return "delete.success";

			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/getThumbnail.do", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getThumbnail(HttpServletRequest request, HttpSession session) throws Exception{
		
		String thumbnailURL = request.getParameter("thumnailURL");
		ResponseEntity<byte[]> response;		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				response = playlistAPIService.getThumbnail(thumbnailURL, session.getAttribute("token").toString());
			}else{
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}	
		
		return response;
	}

	@RequestMapping(value="/details.do")
	public String navToPlaylistDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
			
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				CmAPIMedia mediaAPI = new CmAPIMediaImpl();
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				String playlistId = request.getParameter("playlistId");					
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				Map<String,Object> playlistDetail = playlistAPIService.findPlaylistById(playlistId, null, credential);
				playlistAPIService.generatePlaylistItems(playlistId, null, credential);
				
				int imageDuration = (int) playlistDetail.get("imageDuration");
				int htmlDuration = (int) playlistDetail.get("htmlDuration");
				String calImageDuration = StringUtils.secToTime(imageDuration);
				String calHtmlDuration = StringUtils.secToTime(htmlDuration);
				playlistDetail.put("calImageDuration", calImageDuration);
				playlistDetail.put("calHtmlDuration", calHtmlDuration);
											
				model.addAttribute("playlistId", playlistId);
				model.addAttribute("playlistDetail", playlistDetail);
				model.addAttribute("playlistItems", playlistAPIService.getPlaylistItems());
				
				mediaAPI.generateList(credential, "?limit=0&offset=0&sort=name");
				model.addAttribute("mediaList", mediaAPI.getMediaList());
				
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "playlistDetails";		
	}
	
	@RequestMapping(value="/addItems.do", method = RequestMethod.GET)
	@ResponseBody
	public String addPlaylistItems(HttpServletRequest request, HttpSession session) throws Exception{
		
		String mediaIds = request.getParameter("mediaIds");
		String playlistId = request.getParameter("playlistId");
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());		
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				Map<String,Object> response = playlistAPIService.addPlaylistItems(playlistId, mediaIds, credential);
				ObjectMapper om = new ObjectMapper();
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
	
	@RequestMapping(value="/updateSingle.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateSinglePlaylist(HttpServletRequest request, HttpSession session) throws Exception{
		
		String playlistId = request.getParameter("playlistId");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String imageDuration = request.getParameter("imageDuration");
		String htmlDuration = request.getParameter("htmlDuration");
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());	
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				Map<String,Object> requestParam = new HashMap<String, Object>();
				requestParam.put("name", name);
				requestParam.put("description", description);
				requestParam.put("imageDuration", StringUtils.timeToSec(imageDuration));
				requestParam.put("htmlDuration", StringUtils.timeToSec(htmlDuration));
				requestParam.put("playlistItems", playlistAPIService.getPlaylistItems());
				
				Map<String,Object> response = playlistAPIService.updateSinglePlaylist(playlistId, requestParam, credential);
				ObjectMapper om = new ObjectMapper();
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
	
	@RequestMapping(value="/removeItems.do", method = RequestMethod.POST)
	@ResponseBody
	public String removePlaylistItems(HttpServletRequest request, HttpSession session) throws Exception{
		
		String playlistId = request.getParameter("playlistId");	
		String ItemIds = request.getParameter("ItemIds");
		String name = request.getParameter("name");
		ObjectMapper om = new ObjectMapper();
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());		
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				Map<String,Object> requestParam = new HashMap<String, Object>();
				String ItemId[] = ItemIds.split(",");
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> playlistItemsList = (List<Map<String, Object>>) playlistAPIService.getPlaylistItems();	
				Iterator<Map<String, Object>> iter = playlistItemsList.iterator();
				Map<String,Object> playlistItem = new HashMap<String, Object>();
							
				for(int i=0; i<ItemId.length; i++){
					iter = playlistItemsList.iterator();
					while(iter.hasNext())  
			        {  
						playlistItem = iter.next();
						if((int)playlistItem.get("id") == Integer.parseInt(ItemId[i])){
							playlistItem.put("deleteFlag", true);
						}		              
			        }
				}
				requestParam.put("name", name);
				requestParam.put("playlistItems", playlistItemsList);
				Map<String,Object> response = playlistAPIService.updateSinglePlaylist(playlistId, requestParam, credential);
				
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
	
	@RequestMapping(value="/new.do", method = RequestMethod.POST)
	@ResponseBody
	public String newNormalPlaylist(HttpServletRequest request, HttpSession session) throws Exception{
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String playlistType = request.getParameter("playlistType");
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());	
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				Map<String,String> requestParam = new HashMap<String, String>();
				requestParam.put("name", name);
				requestParam.put("description", description);
				requestParam.put("playlistType", playlistType);
				
				Map<String,Object> response = playlistAPIService.newNormalPlaylist(requestParam, credential);
				ObjectMapper om = new ObjectMapper();
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
