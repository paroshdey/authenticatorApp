package com.authenticator.authenticatorApp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.authenticator.authenticatorApp.entity.AuthFailureDTO;
import com.authenticator.authenticatorApp.repository.AuthFailureLoggingrepository;

@Component
public class AuthenticationEvents {
	
	@Autowired 
	AuthFailureLoggingrepository authFailureLoggingrepository;
	
	@EventListener
    public void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
		AuthFailureDTO authdto = new AuthFailureDTO();
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		Authentication authentication = event.getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        
        
        String username = (String) authentication.getPrincipal();
        String ipAddress = details.getRemoteAddress();
        
        authdto.setUsername(username);
        authdto.setIp(ipAddress);
        authdto.setTimeStamp(timeStamp);
        authFailureLoggingrepository.save(authdto);
        
    }

}
