package com.cmos.bj.ngtask.task.impl.sftp;

import com.alibaba.druid.util.StringUtils;
import com.cmos.bj.ngtask.enums.TaskStatusEnum;
import com.cmos.bj.ngtask.model.SftpCfg;
import com.cmos.bj.ngtask.model.Task;
import com.cmos.bj.ngtask.repository.SftpCfgRepository;
import com.cmos.bj.ngtask.repository.TaskRepository;
import com.cmos.bj.ngtask.task.AbsSftpTask;
import com.cmos.bj.ngtask.utils.SftpUtils;
import com.cmos.bj.ngtask.utils.TimeUtils;
import com.jcraft.jsch.ChannelSftp;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/23 11:39
 */
@Data
@Component
@Scope("prototype")
public class SftpTaskImpl extends AbsSftpTask {

    private static final Logger logger = LoggerFactory.getLogger(SftpTaskImpl.class);

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
        SftpCfg sftpCfg = getSftpCfg();

        Task task = getTaskCfg();

        if (TaskStatusEnum.DISABLE.getCode().equals(task.getTaskStatus())) {
            return;
        }

        //处理remotePath 如果文件名与时间有关，生成当前文件名
        if (sftpCfg.getSftpRemotePath().indexOf("{") >= 0) {
            try {
                String timeReg = sftpCfg.getSftpRemotePath().substring(sftpCfg.getSftpRemotePath().indexOf("{") + 1, sftpCfg.getSftpRemotePath().indexOf("}"));
                sftpCfg.setSftpRemotePath(sftpCfg.getSftpRemotePath().replace("{" + timeReg + "}", TimeUtils.getNowTimeStr(timeReg)));
            }catch (Exception e) {
                logger.error("获取remotePath中的时间配置发生错误", e);
            }
        }

        //获取sftp连接
        ChannelSftp channelSftp = SftpUtils.connectSftpServer(sftpCfg.getSftpAddr(), sftpCfg.getSftpPort(), sftpCfg.getSftpUserName(), sftpCfg.getSftpUserPasswd(), sftpCfg.getSftpEncoding(), 1);

        boolean result = SftpUtils.downloadFiles(channelSftp, sftpCfg.getSftpRemotePath(), taskLocalDir + task.getTaskName() + "/" + sftpCfg.getSftpAddr() + "/" + (StringUtils.isEmpty(sftpCfg.getSftpLocalPath()) ? "" : sftpCfg.getSftpLocalPath()), sftpCfg.getRecursion());

        if (result) {
            logger.info("任务执行完毕：{}", task.getTaskName());
        } else {
            logger.info("任务执行失败：{}", task.getTaskName());
        }

        SftpUtils.close(channelSftp);
    }
}
