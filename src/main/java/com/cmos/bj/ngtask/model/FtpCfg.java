package com.cmos.bj.ngtask.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/21 10:53
 */
@Data
@Entity
public class FtpCfg {

    @Id
    @Column
    private int ftpId;

    @Column
    private String ftpAddr;

    @Column
    private int ftpPort;

    @Column
    private String ftpUserName;

    @Column
    private String ftpUserPasswd;

    @Column
    private String ext;

}
