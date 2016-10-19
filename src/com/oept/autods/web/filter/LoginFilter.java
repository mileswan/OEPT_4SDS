package com.oept.autods.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/06/10
 * Description: User login authentication filter action.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
public class LoginFilter implements Filter {

	private static Log logger = LogFactory.getLog(LoginFilter.class);
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Get request,response,session
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();

        // get URI
        String path = servletRequest.getRequestURI();
        
        // get username from session
        String userName = (String) session.getAttribute("username");
        
        // Ignore filter action when login page or logout action
        if(path.indexOf("/login.") > -1 || path.indexOf("/logout.") > -1 ||
        		path.equals("/OEPT_4SDS/")) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        // redirect to login page if session has no value
        if (userName == null || "".equals(userName)) {
            // do redirect
        	servletResponse.sendRedirect("/OEPT_4SDS/");
            logger.info("Invalid user session, require to login firstly!");
        } else {
            // ignore filter action
            chain.doFilter(request, response);
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
