package com.authenticator.authenticatorApp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/login"})
    public String hello(Model model) {
        return "login";
    }
    
    @RequestMapping({ "/index" })
    public String index(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	model.addAttribute("auth", authentication);
    	return "userdetails";
    }
    
    @GetMapping("/userhome")
    public String userHome(Model model) {
        return "userhome";
    }
    
    @GetMapping("/adminhome")
    public String adminHome(Model model) {
        return "adminhome";
    }
    
   
}
