package com.cmos.bj.ngtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/11/7 15:10
 */
@Controller
public class LoginController {

    @RequestMapping({"/", "login.html", "login"})
    public String login() {
        return "login";
    }

    @RequestMapping("navigator")
    public String navigator() {
        return "navigator";
    }

}
