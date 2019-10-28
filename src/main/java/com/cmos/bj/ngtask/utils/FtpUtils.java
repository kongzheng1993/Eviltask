package com.cmos.bj.ngtask.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * @Description: ftp工具类
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/11 10:18
 */
public class FtpUtils {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    /**
     * 连接ftp服务器
     */
    public static FTPClient connectFtpServer(String addr, int port, String username, String password, String controlEncoding, int fileType) {
        FTPClient ftpClient = new FTPClient();

            ftpClient.setControlEncoding(controlEncoding);
        try {
            ftpClient.connect(addr, port);
        if (StringUtils.isEmpty(username)) {
                ftpClient.login("Anonymous", "");
            } else {
                ftpClient.login(username, password);
            }

            //传输的文件类型
            ftpClient.setFileType(fileType);

            //确认应答状态码是否正确，凡是2开头的isPositiveCompletion都会返回true，因为底层判断那是>=200
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                //如果FTP服务器响应错误 中断传输、断开连接
                //abort：中断正在进行的文件传输，成功返回true，否则false；disconnect：断开与服务器的连接，并恢复默认参数值
                ftpClient.abort();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            logger.error("连接ftp服务器出现错误------\n", e);
        }

        return ftpClient;
    }

    /**
     * 关闭ftp连接
     */
    public static FTPClient closeFtpConnect(FTPClient ftpClient) {

        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.abort();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("关闭ftp服务器出现错误------\n", e);
            }
        }
        return ftpClient;
    }


    /**
     * 文件下载
     * ftpClient ftp连接
     * remotePath 远程目录
     * localPath 本地目录
     * recursion 是否递归
     */
    public static boolean downloadFiles(FTPClient ftpClient, String remotePath, String localPath, int recursion) {

        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            logger.info("ftp连接已断开或不可用，无法下载文件------\n");
            return false;
        }

        if (StringUtils.isEmpty(remotePath) || StringUtils.isEmpty(localPath)) {
            logger.info("ftp远程目录或本地目录为空，无法下载文件------\n");
            return false;
        }

        ftpClient.enterLocalPassiveMode();

        //获取远程主机目录下的文件列表
        FTPFile[] ftpFiles = new FTPFile[0];
        //获取远程父目录
        String workDir = remotePath.substring(0, remotePath.lastIndexOf("/"));
        if (StringUtils.isEmpty(workDir)) {
            workDir = "/";
        }
        try {
            ftpFiles = ftpClient.listFiles(workDir);
        } catch (IOException e) {
            logger.error("ftp获取远程目录下文件列表出现错误------\n", e);
        }

        if (ftpFiles.length == 0) {
            logger.info("ftp远程目录下没有文件，无法下载------\n");
            return false;
        }

        for (FTPFile f : ftpFiles) {
            if (f.isFile()) {
                //创建本地文件
                File localFile = new File(localPath, f.getName());
                //如果本地文件父目录不存在，会报错，所以要创建目录
                if (!localFile.getParentFile().exists()) {
                    localFile.getParentFile().mkdir();
                }
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(localFile);
                } catch (FileNotFoundException e) {
                    logger.error("获取本地文件输出流出现错误------\n", e);
                }

                //移动到工作目录
                try {
                    ftpClient.changeWorkingDirectory(workDir);
                } catch (IOException e) {
                    logger.error("移动到远程工作目录出现错误------\n", e);
                }

                try {
                    ftpClient.retrieveFile(f.getName(), outputStream);
                } catch (IOException e) {
                    logger.error("下载文件出错------\n", e);
                }

                try {
                    outputStream.flush();
                } catch (IOException e) {
                    logger.error("数据从输出流刷到磁盘时出错------\n", e);
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("关闭输出流时出错", e);
                }
                logger.info("文件下载完成------" + f.getName());
            } else if (f.isDirectory()){
                if (1 == recursion) {
                    //递归
                    downloadFiles(ftpClient, remotePath, localPath, recursion);
                }
            }
        }
        return true;
    }


}
