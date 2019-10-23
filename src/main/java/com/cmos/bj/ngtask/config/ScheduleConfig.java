package com.cmos.bj.ngtask.config;

import com.cmos.bj.ngtask.model.SpringSchedule;
import com.cmos.bj.ngtask.repository.SpringScheduleRepository;
import com.cmos.bj.ngtask.task.ScheduleOfTask;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

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

    @Autowired
    private SpringScheduleRepository springScheduleRepository;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
        for (SpringSchedule springSchedule : springScheduleRepository.findAll()) {
            Class<?> clazz;
            Object task;

            try {
                clazz = Class.forName(springSchedule.getCronClassName());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("spring_scheduled表数据" + springSchedule.getCronClassName() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(springSchedule.getCronClassName() + "未在spring ioc容器中找到", e);
            }

            Assert.isAssignable(ScheduleOfTask.class, task.getClass(), "定时任务必须实现ScheduleOfTask接口");

            // 通过改变数据库数据实现动态修改定时任务
            scheduledTaskRegistrar.addTriggerTask(((Runnable) task), triggerContext -> {
                String cronExpression = (springScheduleRepository.findById(springSchedule.getCronId())).get().getCronExpression();
                return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
            });
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
    public void addTriggerTask(String taskId, TriggerTask triggerTask) {
        if (scheduledTasks.contains(taskId)) {
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }

        TaskScheduler scheduler = scheduledTaskRegistrar.getScheduler();
        ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());

    }
    


}
