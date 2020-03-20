package com.qym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminRouterController {

    @GetMapping("/admin")
    public String adminIndex(){ return "admin/frame"; }

    @PostMapping("/toLogin")
    public String login(){ return "redirect:/admin"; }

    @GetMapping("/toLogin")
    public String login2(){
        return "login";
    }
}
