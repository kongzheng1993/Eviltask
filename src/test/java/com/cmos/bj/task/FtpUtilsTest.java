package com.cmos.bj.task;

import com.cmos.bj.ngtask.utils.FtpUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/11 17:58
 */
public class FtpUtilsTest {

    @Test
    public void Test() {
        FTPClient ftpClient = FtpUtils.connectFtpServer("10.4.144.217", 21, "cboss", "cboss-123", "gbk", FTPClient.ASCII_FILE_TYPE);
        FtpUtils.downloadFiles(ftpClient, "/20190926-10-0-ZX.txt", "E:\\asiainfo\\接口平台\\requirement\\20191008携号转网新增task管理系统", 0);
        FtpUtils.closeFtpConnect(ftpClient);
    }

}
