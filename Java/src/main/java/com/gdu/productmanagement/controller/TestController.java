package com.gdu.productmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
