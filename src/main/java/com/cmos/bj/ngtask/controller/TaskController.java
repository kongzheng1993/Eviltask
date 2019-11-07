package com.cmos.bj.ngtask.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmos.bj.ngtask.service.TaskService;
import com.cmos.bj.ngtask.utils.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/22 18:21
 */
@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/Task")
    public String getAllTask(Model model) {
        model.addAttribute("task", taskService.getAllTask());
        return "task";
    }


}
