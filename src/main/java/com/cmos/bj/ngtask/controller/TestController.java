package com.cmos.bj.ngtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/11/5 11:01
 */
@Controller
public class TestController {

    @RequestMapping("test")
    public String test(Model model) {
        model.addAttribute("param1", "fuck out");
        return "test";
    }

}
