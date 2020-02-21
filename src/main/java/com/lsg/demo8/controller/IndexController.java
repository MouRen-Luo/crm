package com.lsg.demo8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    //登录跳转
    @RequestMapping(value = "/login")
    public String index(){
        return "login";
    }

    @RequestMapping(value = "/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403";
    }
}
