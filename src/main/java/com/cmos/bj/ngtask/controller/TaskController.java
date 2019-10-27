package com.cmos.bj.ngtask.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmos.bj.ngtask.utils.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/22 18:21
 */
@RestController
public class TaskController {

    @Autowired
    private TaskUtils taskUtils;

    @RequestMapping("/getAllTask")
    public String getAllTask() {
        return JSONObject.toJSONString(taskUtils.getSchedulerTasks());
    }


}
