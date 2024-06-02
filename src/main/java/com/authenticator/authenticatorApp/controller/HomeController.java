package com.authenticator.authenticatorApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String hello(Model model) {
        return "login";
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
