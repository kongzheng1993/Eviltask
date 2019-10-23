package com.cmos.bj.ngtask.model;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 12:27
 */
@Data
@Entity
@Proxy(lazy = false)
public class Task {

    @Id
    @Column(name = "task_id")
    private int taskId;
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_class_name")
    private String taskClassName;
    @Column(name = "task_expression")
    private String taskExpression;
    @Column(name = "task_desc")
    private String taskDesc;
    @Column(name = "task_status")
    private int taskStatus;
    @Column(name = "after_task")
    private String afterTask;
    @Column(name = "ftp_id")
    private Integer ftpId;
    @Column(name = "sftp_id")
    private Integer sftpId;
}
