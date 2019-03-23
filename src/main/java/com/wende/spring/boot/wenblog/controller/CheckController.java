package com.wende.spring.boot.wenblog.controller;

import com.wende.spring.boot.wenblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/check")
public class CheckController {
    @Autowired
    UserService userService;

    @RequestMapping("/email")
    @ResponseBody
    public String emailUnregistered(@RequestParam(value = "useremail") String userEmail){
        if(userService.checkEmail(userEmail)){
            return "fail";
        }
        return "success";
    }

    @RequestMapping("/alive")
    @ResponseBody
    public String checkServerAlive(){
        return "alive";
    }
}
