package com.learnswedish.learnswedish.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String getIndex(Model model){
        return "index";
    }

    @GetMapping("/jimmy")
    public String getJimmy(Model model){
        return "jimmy";
    }

}
