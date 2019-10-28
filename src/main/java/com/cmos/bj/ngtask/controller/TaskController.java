package com.cmos.bj.ngtask.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmos.bj.ngtask.config.ScheduleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/22 18:21
 */
@RestController
public class TaskController {

    @Autowired
    private ScheduleConfig scheduleConfig;

    @RequestMapping("/getAllTask")
    public String getAllTask() {
        Set<ScheduledTask> tasks =  scheduleConfig.getScheduledFutures();
        JSONObject jsonObject = new JSONObject();
        for (ScheduledTask task : tasks) {
            jsonObject.put(task.getTask().toString(), task.getTask().getRunnable().toString());
        }
        return jsonObject.toJSONString();
    }


}
