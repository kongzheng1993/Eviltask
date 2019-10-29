package com.cmos.bj.ngtask.utils;


import com.cmos.bj.ngtask.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class TaskUtils {

    private final static Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    private static Map<String, ScheduledFuture<?>> futureMap;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 初始化任务列表
     */
    @PostConstruct
    public void init() {
        futureMap = new ConcurrentHashMap<>();
    }

    /**
     * 创建ThreadPoolTaskScheduler线程池
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(8);
        return threadPoolTaskScheduler;
    }

    /**
     * 添加定时任务
     */
    public boolean addTask(Runnable task, Trigger trigger, String taskName) {
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(task, trigger);
        ScheduledFuture oldScheduledFuture = futureMap.put(taskName, future);
        if (oldScheduledFuture == null) {
            logger.info("添加任务成功：" + taskName);
            return true;
        } else {
            logger.info("添加taskName为" + taskName + "的任务重复");
            return false;
        }
    }

    /**
     * 移除定时任务
     */
    public boolean removeTask(String taskName) {
        ScheduledFuture toBeRemovedFuture = futureMap.remove(taskName);
        if (toBeRemovedFuture != null) {
            toBeRemovedFuture.cancel(true);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 更新定时任务
     */
    public boolean updateTask(Runnable task, Trigger trigger, String taskName) {
        ScheduledFuture toBeUpdatedFuture = futureMap.remove(taskName);
        if (toBeUpdatedFuture != null) {
            toBeUpdatedFuture.cancel(true);
            logger.info("取消定时任务成功" + taskName);
            return addTask(task, trigger, taskName);
        } else {
            logger.info("更新的任务不存在，taskName为：" + taskName);
            return false;
        }
    }

    /**
     * 获取threadPoolTaskScheduler中的任务
     */
    public Map<String, ScheduledFuture<?>> getSchedulerTasks() {
        return futureMap;
    }


}
