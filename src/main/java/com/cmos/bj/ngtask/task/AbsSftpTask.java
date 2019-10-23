package com.cmos.bj.ngtask.task;

import com.cmos.bj.ngtask.model.SftpCfg;
import com.cmos.bj.ngtask.model.Task;
import org.springframework.util.Assert;

import java.time.LocalTime;

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
        Task task = getTaskCfg();

        //获取sftp配置
        SftpCfg sftpCfg = getSftpCfg();

        //任务执行流程
        doTask();

        //判断后置操作
        for (String afterOpr : task.getAfterTask().split(FTP_AFTER_OPR_SEPARATOR)) {
            if (afterOpr.contains("CREATEFILE")) {

                Assert.doesNotContain(afterOpr, ":", "请检查配置，CREATEFILE操作需要格式为“CREATEFILE:文件名”");

                String createFileName = afterOpr.substring(afterOpr.indexOf(":"));

                String postfix = createFileName.substring(createFileName.indexOf("{") + 1, createFileName.indexOf("}"));

                //todo 生成文件名中的时间是取数据的时间，还是要和取的数据中文件名一致


            } else {
                switch (afterOpr) {
                    case "DEL":
                        break;
                    case "TRANS":
                        //todo
                        break;
                    default:
                        break;
                }
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
    public abstract Task getTaskCfg();

    public abstract void doTask();

}
