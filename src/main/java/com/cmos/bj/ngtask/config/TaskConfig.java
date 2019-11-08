package com.cmos.bj.ngtask.config;

import com.cmos.bj.ngtask.entity.Task;
import com.cmos.bj.ngtask.repository.TaskRepository;
import com.cmos.bj.ngtask.task.AbsSftpTask;
import com.cmos.bj.ngtask.task.ScheduleOfTask;
import com.cmos.bj.ngtask.task.impl.file.DeleteLocalFileTaskImpl;
import com.cmos.bj.ngtask.task.impl.ftp.FtpDownloadTaskImpl;
import com.cmos.bj.ngtask.task.impl.sftp.SftpDownloadTaskImpl;
import com.cmos.bj.ngtask.utils.TaskUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * task配置类
 * @author kongz
 */

@Component
public class TaskConfig {

    private final static Logger logger = LoggerFactory.getLogger(TaskConfig.class);

    @Autowired
    private ApplicationContext context;


    @Autowired
    private TaskUtils taskUtils;

    @Autowired
    private TaskRepository taskRepository;

    /**
     * 从数据库查询出所有配置的task，注册到threadPoolTaskScheduler
     */
    @PostConstruct
    private void register() {

        logger.info("开始注册所有任务……");

        //查询出所有的task，遍历注册
        for (Task task : taskRepository.findAll()) {

            //获取Runnable对象
            Class<?> clazz;
            Object object;

            try {
                clazz = Class.forName(task.getTaskClassName());
                object = context.getBean(clazz);
                if (object instanceof AbsSftpTask) {
                    Assert.notNull(task.getSftpId(), task.getTaskName() + ":sftpId不能为空，请检查task表配置");
                    ((SftpDownloadTaskImpl) object).setSftpId(task.getSftpId());
                    ((SftpDownloadTaskImpl) object).setTaskId(task.getTaskId());
                } else if (object instanceof FtpDownloadTaskImpl) {
                    //todo
                } else if (object instanceof DeleteLocalFileTaskImpl) {
                    Assert.notNull(task.getFileId(), task.getFileId() + ":fileId不能为空，请检查task表配置");
                    ((DeleteLocalFileTaskImpl) object).setTaskId(task.getTaskId());
                    ((DeleteLocalFileTaskImpl) object).setFileId(task.getFileId());
                }
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("spring_scheduled表数据" + task.getTaskName() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(task.getTaskClassName() + "未在spring ioc容器中找到", e);
            }

            Assert.isAssignable(ScheduleOfTask.class, object.getClass(), "定时任务必须实现ScheduleOfTask接口");

            try {
                Boolean result = taskUtils.addTask((Runnable) object, new CronTrigger(task.getTaskExpression()), task.getTaskName());

                if (result) {
                    logger.info(task.getTaskName() + "任务添加成功");
                } else {
                    logger.info(task.getTaskName() + "任务添加失败！！！");
                }
            } catch (Exception e) {
                logger.error("启动时向threadPoolTaskScheduler中注册任务失败： " + task.getTaskName(), e);
            }

        }


    }



}
