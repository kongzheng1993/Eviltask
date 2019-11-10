package com.cmos.bj.ngtask.controller;

import com.cmos.bj.ngtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/11/7 15:10
 */
@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping({"/", "login.html", "login"})
    public String login() {
        return "login";
    }

    @RequestMapping("login.do")
    public String doLogin(Model model, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        model.addAttribute("user", userRepository.getByUserNameAndPassword(username, password));
        return "index";
    }

}
