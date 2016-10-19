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
import com.oept.autods.api.service.CmAPIChannel;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/09/06
 * Description: Schedule management when navigate to Marketing network page.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@Controller
@RequestMapping(value="/schedule")
public class ScheduleAct {
	
	@Qualifier("channelAPIService")
    @Autowired
    private CmAPIChannel channelAPIService;
	@Qualifier("authAPIService")
	@Autowired
	private CmAPIAuth authAPIService;
	private static final Log logger = LogFactory.getLog(ScheduleAct.class);
	
	@RequestMapping(value="/info.do")
	public String navToChannelList(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){
				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());			
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				//channelAPIService.generateList(credential, "?limit=0&offset=0&sort=name");
				//model.addAttribute("channelList", channelAPIService.getChannelList());
			}else{
				return "redirect:/auth/logout.do";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "scheduleCalendar";		
	}

	@RequestMapping(value="/list.do")
	public String navToTimeslotList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){

				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String channelId = request.getParameter("channelId");
				String channelName = request.getParameter("channelName");
				String frameId = request.getParameter("frameId");
				Map<String,Object> timeslots = channelAPIService.findTimeslots(channelId, frameId, "offset=0&limit=999999&fromDate=2015-9-6&toDate=2015-9-12", credential);
											
				model.addAttribute("channelId", channelId);
				model.addAttribute("channelName", channelName);
				model.addAttribute("timeslots", timeslots);
				
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "scheduleList";		
	}
	
	@RequestMapping(value="/details.do")
	public String navToTimeslotDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try {
			if(authAPIService.ping(session.getAttribute("apiToken").toString())){

				Map<String,String> credential = new HashMap<String, String>();
				credential.put("username", session.getAttribute("username").toString());
				credential.put("password", session.getAttribute("password").toString());
				credential.put("apiToken", session.getAttribute("apiToken").toString());
				
				String channelId = request.getParameter("channelId");
				//String timeslotId = request.getParameter("timeslotId");
				Map<String,Object> timeslots = channelAPIService.findTimeslots(channelId, "1", null, credential);
											
				model.addAttribute("channelId", channelId);
				model.addAttribute("timeslots", timeslots);
				
			}else{
				return "redirect:/auth/logout.do";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		
		return "scheduleDetails";		
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateSchedule(HttpServletRequest request, HttpSession session) throws Exception{	

		return null;
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public String deleteScheduleId(HttpServletRequest request, HttpSession session) throws Exception{
		
		return null;
	}
	
	@RequestMapping(value="/create.do", method = RequestMethod.POST)
	@ResponseBody
	public String createSchedule(HttpServletRequest request, HttpSession session) throws Exception{
		
		return null;
	}
}
