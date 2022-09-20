package com.guigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    //去首页
    @RequestMapping("/")
    public String index(){
        return "frame/index";
    }
    //去主页面
    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }
}
