package com.github.AuthanticationServer.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageViewController {

    @GetMapping("/login")
    public String pageLogin() {
        return "login";
    }

    @GetMapping("/home")
    @ResponseBody
    public String pageHome(Authentication authentication) {
        return "Olá, " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String autorizationCode(@RequestParam("code") String code){
        return code;
    }
}
