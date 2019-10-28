package com.cmos.bj.ngtask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

}
