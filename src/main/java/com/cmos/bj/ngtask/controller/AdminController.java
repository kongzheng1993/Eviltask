package com.cmos.bj.ngtask.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmos.bj.ngtask.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: kongz
 */

@RestController
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin")
    public String getAllAdmin() {
        return JSONObject.toJSONString(adminRepository.findAll());
    }
}
