package com.cmos.bj.ngtask.controller;

import com.cmos.bj.ngtask.service.TaskService;
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

    @RequestMapping("/task.html")
    public String getAllTask(Model model) {
        model.addAttribute("tasks", taskService.getAllTask());
        return "task";
    }


}
