package com.cmos.bj.ngtask.model;

import lombok.Data;

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
public class SpringSchedule {

    @Id
    @Column(name = "cron_id")
    private int cronId;
    @Column(name = "cron_class_name")
    private String cronClassName;
    @Column(name = "cron_expression")
    private String cronExpression;
    @Column(name = "task_desc")
    private String taskDesc;
    @Column(name = "status")
    private int status;

}
