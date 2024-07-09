package com.authenticator.authenticatorApp.controller;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping({"/", "/login"})
    public String login(Model model) {
    	logger.debug("in HomeController :: login()");
        return "login";
    }
    @PostMapping({ "/index" })
    public String index(Model model) {
    	logger.debug("in HomeController :: index()");
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	model.addAttribute("auth", authentication);
    	return "userdetails";
    } 
    @GetMapping({ "/oauth" })
    public String oauth(Model model , @AuthenticationPrincipal OAuth2User principal) {
    	logger.debug("in HomeController :: oauth()");
    	model.addAttribute("name", principal.getAttribute("name"));
    	return "userdetails";
    } 
}
