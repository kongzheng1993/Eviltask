package com.cmos.bj.ngtask.task.impl;

import com.cmos.bj.ngtask.task.ScheduleOfTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 16:10
 */
@Component
public class DemoTask implements ScheduleOfTask {

    private static final Logger logger = LoggerFactory.getLogger(DemoTask.class);

    @Override
    public void execute() {
        logger.info(LocalTime.now().toString());
    }
}
