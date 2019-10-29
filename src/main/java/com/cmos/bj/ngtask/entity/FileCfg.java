package com.cmos.bj.ngtask.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/29 18:18
 */
@Data
@Entity
@Proxy(lazy = false)
public class FileCfg {

    @Id
    @Column(name = "file_id")
    private int fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_local_path")
    private String fileLocalPath;

    @Column(name = "file_remote_path")
    private String fileRemotePath;

    @Column(name = "sftp_id")
    private Integer sftpId;

    @Column(name = "ftp_id")
    private Integer ftpId;

    @Column(name = "recursive")
    private boolean recursive;

    @Column(name = "ext")
    private String ext;

}
