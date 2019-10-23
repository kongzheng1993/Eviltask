package com.cmos.bj.ngtask.config;

import com.alibaba.druid.util.StringUtils;
import com.cmos.bj.ngtask.model.Task;
import com.cmos.bj.ngtask.repository.TaskRepository;
import com.cmos.bj.ngtask.task.AbsSftpTask;
import com.cmos.bj.ngtask.task.ScheduleOfTask;
import com.cmos.bj.ngtask.task.impl.ftp.FtpTaskImpl;
import com.cmos.bj.ngtask.task.impl.sftp.SftpTaskImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 11:24
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Autowired
    private ApplicationContext context;

    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    private Set<ScheduledTask> scheduledTasks = null;

    /**
     *      全局变量，记录所有task，标记taskID
     */
    private static final Map<Integer, String> ALL_SCHEDULED_TASKS = new HashMap<>();

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
        for (Task task : taskRepository.findAll()) {
            Class<?> clazz;
            Object taskObj;

            Assert.notNull(task.getSftpId(), task.getTaskName() + ":sftpId不能为空，请检查task表配置");

            try {
                clazz = Class.forName(task.getTaskClassName());
                taskObj = context.getBean(clazz);
                if (taskObj instanceof AbsSftpTask) {
                    ((SftpTaskImpl) taskObj).setSftpId(task.getSftpId());
                    ((SftpTaskImpl) taskObj).setTaskId(task.getTaskId());
                } else if (taskObj instanceof FtpTaskImpl) {
                    //todo
                }
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("spring_scheduled表数据" + task.getTaskName() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(task.getTaskClassName() + "未在spring ioc容器中找到", e);
            }

            Assert.isAssignable(ScheduleOfTask.class, taskObj.getClass(), "定时任务必须实现ScheduleOfTask接口");

            // 通过改变数据库数据实现动态修改定时任务

            scheduledTaskRegistrar.addTriggerTask(((Runnable) taskObj), triggerContext -> {
                String cronExpression = task.getTaskExpression();
                return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
            });
            // 记录到全局ALL_SCHEDULED_TASKS
            ALL_SCHEDULED_TASKS.put(task.getTaskId(), task.getTaskName());
        }
    }


    /**
     * 获取所有任务
     */
    public Set<ScheduledTask> getScheduledFutures() {
        if (scheduledTasks == null) {
            scheduledTasks = scheduledTaskRegistrar.getScheduledTasks();
        }
        return scheduledTasks;
    }

    /**
     * 添加任务
     */
    public void addTriggerTask(int taskId, TriggerTask triggerTask) {
        if (ALL_SCHEDULED_TASKS.containsKey(taskId)) {
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }

        //根据taskID获取数据库配置
        TaskScheduler taskScheduler = scheduledTaskRegistrar.getScheduler();
        ScheduledFuture<?> future = taskScheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
        //ScheduledTask scheduledTask = new ScheduledTask(triggerTask);

    }
    


}
