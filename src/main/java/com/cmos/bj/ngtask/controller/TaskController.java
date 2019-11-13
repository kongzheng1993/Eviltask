package com.cmos.bj.ngtask.controller;

import com.cmos.bj.ngtask.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/22 18:21
 */
@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/task")
    public String getAllTask(Model model, Pageable pageable) {
        model.addAttribute("task", taskRepository.findAll(pageable));
        return "task";
    }


}
