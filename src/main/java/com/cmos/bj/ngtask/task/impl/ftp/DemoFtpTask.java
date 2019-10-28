package com.cmos.bj.ngtask.task.impl.ftp;


import com.cmos.bj.ngtask.task.ScheduleOfTask;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 18:54
 */
@Component
public class DemoFtpTask implements ScheduleOfTask {

    private static final Logger logger = LoggerFactory.getLogger(DemoFtpTask.class);

    @Override
    public void execute() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("gbk");
        try {
            ftpClient.connect("10.4.144.217", 21);

            ftpClient.login("cboss", "cboss-123");

            FTPFile[] ftpFiles = ftpClient.listFiles();
            logger.info("当前目录" + ftpClient.pwd());
            for (FTPFile f: ftpFiles) {

                logger.info(f.getName());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
