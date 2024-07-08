package com.authenticator.authenticatorApp.service;

import com.authenticator.authenticatorApp.entity.AuthFailureDTO;

public interface AuthFailureLogService {
	
	
	void saveAuthFailureLog(AuthFailureDTO auth);

}
