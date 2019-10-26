package com.cmos.bj.ngtask.task;

import com.cmos.bj.ngtask.model.SftpCfg;
import com.cmos.bj.ngtask.model.Task;
import com.cmos.bj.ngtask.utils.SftpUtils;
import com.cmos.bj.ngtask.utils.TimeUtils;
import com.jcraft.jsch.ChannelSftp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.io.*;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/21 10:37
 */
public abstract class AbsSftpTask implements ScheduleOfTask {

    private static final Logger logger = LoggerFactory.getLogger(AbsSftpTask.class);

    private static String FTP_AFTER_OPR_SEPARATOR = "\\|";

    @Override
    public void execute() {

        //获取任务配置
        Task task = getTaskCfg();

        //获取sftp配置
        SftpCfg sftpCfg = getSftpCfg();

        //任务执行流程
        doTask();

        //判断后置操作
        for (String afterOpr : task.getAfterTask().split(FTP_AFTER_OPR_SEPARATOR)) {
            if (afterOpr.contains("CREATEFILE")) {

                Assert.isTrue(afterOpr.contains(":"), "请检查配置，CREATEFILE操作需要格式为“CREATEFILE:文件名”");

                String createFileName = afterOpr.substring(afterOpr.indexOf(":") + 1);

                String postfix = createFileName.substring(createFileName.indexOf("{") + 1, createFileName.indexOf("}"));

                //todo 生成文件名中的时间是取数据的时间，还是要和取的数据中文件名一致

                String timeInFileName = TimeUtils.getNowTimeStr(postfix);

                createFileName = createFileName.replace(createFileName.substring(createFileName.indexOf("{"), createFileName.indexOf("}") + 1), timeInFileName);

                ChannelSftp channelSftp = SftpUtils.connectSftpServer(sftpCfg.getSftpAddr(), sftpCfg.getSftpPort(), sftpCfg.getSftpUserName(), sftpCfg.getSftpUserPasswd(), sftpCfg.getSftpEncoding(), 1);

                File verfFile = new File((StringUtils.isEmpty(sftpCfg.getSftpLocalPath()) ? "" : (sftpCfg.getSftpLocalPath().endsWith("/") ? sftpCfg.getSftpLocalPath() : sftpCfg.getSftpLocalPath() + "/")) + createFileName);

                if (!verfFile.exists()) {
                    try {
                        verfFile.createNewFile();
                    } catch (IOException e) {
                        logger.error("创建本地verf文件失败", e);
                    }
                }

                InputStream inputStream= null;

                try {
                    inputStream = new FileInputStream(verfFile);
                } catch (FileNotFoundException e) {
                    logger.error("verfFile获取输入流失败", e);
                }

                if (inputStream != null) {
                    SftpUtils.uploadFile(channelSftp, sftpCfg.getSftpRemotePath().substring(0, sftpCfg.getSftpRemotePath().lastIndexOf("/") + 1) + createFileName, inputStream);
                } else {
                    logger.info("从本地verf文件获取的inputstream为空，无法uploadFile");
                }


            } else {
                switch (afterOpr) {
                    case "DEL":
                        break;
                    case "TRANS":
                        //todo
                        break;
                    default:
                        break;
                }
            }
        }


    }

    /**
     * 获取任务配置
     * @return
     */
    //public abstract SpringSchedule getTaskCfg();

    /**
     * 获取sftp配置
     *
     * @return
     */
    public abstract SftpCfg getSftpCfg();

    /**
     * 获取task配置
     *
     * @return
     */
    public abstract Task getTaskCfg();

    public abstract void doTask();

}
