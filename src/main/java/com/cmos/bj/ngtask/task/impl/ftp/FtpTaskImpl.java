package com.cmos.bj.ngtask.task.impl.ftp;

import com.cmos.bj.ngtask.entity.SftpCfg;
import com.cmos.bj.ngtask.entity.Task;
import com.cmos.bj.ngtask.repository.SftpCfgRepository;
import com.cmos.bj.ngtask.repository.TaskRepository;
import com.cmos.bj.ngtask.task.AbsSftpTask;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/23 11:39
 */
@Data
@Component
public class FtpTaskImpl extends AbsSftpTask {

    private static final Logger logger = LoggerFactory.getLogger(FtpTaskImpl.class);

    private int sftpId;

    private int taskId;

    @Value("${task.localDir}")
    private String taskLocalDir;

    @Autowired
    private SftpCfgRepository sftpCfgRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public SftpCfg getSftpCfg() {
        return sftpCfgRepository.getOne(sftpId);
    }

    @Override
    public Task getTaskCfg() {
        return taskRepository.getOne(taskId);
    }

    @Override
    public void doTask() {

    }
}
