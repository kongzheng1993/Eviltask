package com.cmos.bj.ngtask.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/11/7 12:12
 */
@Entity
@Data
public class Admin {

    @Id
    @Column(name = "admin_id")
    private int adminId;
    @Column(name = "admin_name")
    private String adminName;
    @Column(name = "password")
    private String password;
    @Column(name = "tel")
    private String tel;
    @Column(name = "name")
    private String name;

}
