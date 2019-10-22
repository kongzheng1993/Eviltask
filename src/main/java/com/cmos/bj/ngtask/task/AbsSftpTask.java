package com.cmos.bj.ngtask.task;

import com.cmos.bj.ngtask.enums.AfterOprEnum;
import com.cmos.bj.ngtask.model.SftpCfg;
import com.cmos.bj.ngtask.model.SpringSchedule;
import com.cmos.bj.ngtask.utils.SftpUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/21 10:37
 */
public abstract class AbsSftpTask implements ScheduleOfTask {

    private static String FTP_AFTER_OPR_SEPARATOR = "|";

    @Override
    public void execute() {

        //获取任务配置
        SpringSchedule springSchedule = getTaskCfg();

        //获取sftp配置
        SftpCfg sftpCfg = getSftpCfg();

        //任务执行流程
        doTask();

        //判断后置操作
        for (String afterOpr : springSchedule.getAfterOpr().split(FTP_AFTER_OPR_SEPARATOR)) {
            switch (afterOpr) {
                case "DEL":
                    break;
                case "TRANS":
                    //todo
                    break;
                case "CREATEFILE":
                    //todo
                    break;
                default:
                    break;
            }
        }


    }

    /**
     * 获取任务配置
     * @return
     */
    //public abstract SpringSchedule getTaskCfg();

    /**
     * 获取sftp配置
     *
     * @return
     */
    public abstract SftpCfg getSftpCfg();

    /**
     * 获取task配置
     *
     * @return
     */
    public abstract SpringSchedule getTaskCfg();

    public abstract void doTask();

}
