package com.cmos.bj.ngtask.task.impl.file;

import com.cmos.bj.ngtask.entity.FileCfg;
import com.cmos.bj.ngtask.entity.Task;
import com.cmos.bj.ngtask.enums.TaskStatusEnum;
import com.cmos.bj.ngtask.repository.FileCfgRepository;
import com.cmos.bj.ngtask.repository.TaskRepository;
import com.cmos.bj.ngtask.task.ScheduleOfTask;
import com.cmos.bj.ngtask.utils.TimeUtils;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @Description: 删除本地文件
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/29 14:10
 */
@Data
@Component
@Scope("prototype")
public class DeleteLocalFileTaskImpl implements ScheduleOfTask {

    private static final Logger logger = LoggerFactory.getLogger(DeleteLocalFileTaskImpl.class);

    private int taskId;

    private int fileId;

    @Autowired
    private FileCfgRepository fileCfgRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void execute() {

        FileCfg fileCfg = fileCfgRepository.getOne(fileId);

        Task task = taskRepository.getOne(taskId);

        if (TaskStatusEnum.DISABLE.getCode() == (task.getTaskStatus())) {
            logger.info(task.getTaskName() + "任务已停用，如要启用请修改配置。");
            return;
        }

        if (fileCfg.getFileLocalPath().indexOf("{") >= 0) {
            try {
                String timeReg = fileCfg.getFileLocalPath().substring(fileCfg.getFileLocalPath().indexOf("{") + 1, fileCfg.getFileLocalPath().indexOf("}"));
                fileCfg.setFileLocalPath(fileCfg.getFileLocalPath().replace("{" + timeReg + "}", TimeUtils.getNowTimeStr(timeReg)));
            }catch (Exception e) {
                logger.error("获取remotePath中的时间配置发生错误", e);
            }
        }

        String workDir = fileCfg.getFileLocalPath().substring(0, fileCfg.getFileLocalPath().lastIndexOf("/"));

        String fileReg = fileCfg.getFileLocalPath().substring(fileCfg.getFileLocalPath().lastIndexOf("/") + 1);

        Collection<File> files = FileUtils.listFiles(new File(workDir), null, fileCfg.isRecursive());

        boolean result = true;

        for (File file : files) {

            if (!file.getName().matches(fileReg)) {
                continue;
            }

            try {
                if (file.isFile()) {
                    FileUtils.forceDelete(file);
                    logger.info("删除文件成功：{}", file.getAbsolutePath());
                    result = true;
                } else if (file.isDirectory()) {
                    FileUtils.deleteDirectory(file);
                    logger.info("删除文件夹成功： {}", file.getAbsolutePath());
                    result = true;
                } else {
                    logger.info("对象不是文件，也不是文件夹：{}  {}", task.getTaskName(), file.getAbsolutePath());
                    continue;
                }
            } catch (IOException e) {
                logger.error("删除文件任务出错： {}", task.getTaskName(), e);
                result = false;
            }
        }

        if (result) {
            logger.info("任务执行成功：{}", task.getTaskName());
        } else {
            logger.info("任务执行失败：{}", task.getTaskName());
        }
    }
}
