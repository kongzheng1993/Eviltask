package com.cmos.bj.ngtask.model;

import lombok.Data;
import org.hibernate.annotations.Proxy;

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
@Proxy(lazy = false)
public class SftpCfg {

    @Id
    @Column
    private int sftpId;

    @Column
    private String sftpAddr;

    @Column
    private int sftpPort;

    @Column
    private String sftpUserName;

    @Column
    private String sftpUserPasswd;

    @Column
    private String sftpEncoding;

    @Column
    private String sftpLocalPath;

    @Column
    private String sftpRemotePath;

    @Column
    private int recursion;

    @Column
    private String ext;

}
