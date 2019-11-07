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
public class User {

    @Id
    @Column(name = "user_id")
    private int userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
}
