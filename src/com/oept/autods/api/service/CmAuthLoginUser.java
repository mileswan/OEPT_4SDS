package com.oept.autods.api.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/05/25
 * Description: Object used to invoke Content Manager auth APIs.
 * Copyright (c) 2015 mwan. All rights reserved.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmAuthLoginUser{
    private String _username;
    private String _password;
    private Boolean _rememberMe;

    public String getUsername() {
        return _username;
    }

    public String getPassword() {
        return _password;
    }

    public Boolean getRememberMe() {
        return _rememberMe;
    }
    
    public void setUsername(String username) {
        this._username = username;
    }
    
    public void setPassword(String password) {
    	this._password = password;
    }
    
    public void setRememberMe(boolean rememberMe) {
        this._rememberMe = rememberMe;
    }

}
