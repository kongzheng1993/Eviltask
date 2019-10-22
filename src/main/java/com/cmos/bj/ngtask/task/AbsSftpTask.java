package com.cmos.bj.ngtask.task;

import com.cmos.bj.ngtask.model.SftpCfg;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/21 10:37
 */
public abstract class AbsSftpTask implements ScheduleOfTask {

    @Override
    public void execute() {

        //获取任务配置
        //SpringSchedule springSchedule = getTaskCfg();

        //获取sftp配置
        SftpCfg sftpCfg = getSftpCfg();

        //任务执行流程
        doTask();

    }

    /**
     * 获取任务配置
     * @return
     */
    //public abstract SpringSchedule getTaskCfg();

    /**
     * 获取sftp配置
     * @return
     */
    public abstract SftpCfg getSftpCfg();

    public abstract void doTask();

}
