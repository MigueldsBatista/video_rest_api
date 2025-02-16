package com.example.rea4e.rest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rea4e.security.CustomAuthentication;


@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    
    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication auth) {
        if(auth instanceof CustomAuthentication){
            System.out.println(auth);
        }
        return "Olá "+auth.getName();
    }
    
}
