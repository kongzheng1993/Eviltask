package com.cmos.bj.ngtask.controller;

import com.cmos.bj.ngtask.entity.Admin;
import com.cmos.bj.ngtask.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmin() {
        return adminRepository.findAll();
    }
}
