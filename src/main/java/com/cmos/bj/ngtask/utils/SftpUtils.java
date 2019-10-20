package com.cmos.bj.ngtask.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/14 12:25
 */
public class SftpUtils {

    private static final Logger logger = LoggerFactory.getLogger(SftpUtils.class);

    /**
     * 创建链接
     */
    public static ChannelSftp connectSftpServer(String addr, int port, String username, String password, String controlEncoding, int fileType) {

        JSch jSch = new JSch();
        Session session = null;
        Channel channel = null;
        try {
            session = jSch.getSession(username, addr, port);
        } catch (JSchException e) {
            logger.error("获取sftp session失败", e);
        }
        session.setPassword(password);

        Properties config = new Properties();
        config.setProperty("StrictHostKeyChecking", "no");
        session.setConfig(config);

        try {
            session.setTimeout(30000);
        } catch (JSchException e) {
            logger.error("设置超时时间出现错误", e);
        }

        try {
            session.connect();
        } catch (JSchException e) {
            logger.error("sftp session 连接失败", e);
        }

        logger.info("sftp session 连接成功");

        try {
            channel = session.openChannel("sftp");
        } catch (JSchException e) {
            logger.info("sftp session 打开通道失败");
        }

        try {
            channel.connect();
        } catch (JSchException e) {
            logger.error("建立sftp session channel通道的连接失败");
        }

        logger.info("Connected successfully to ftpHost = " + addr + " on port " + port + " as sftpUserName " + username);

        return (ChannelSftp) channel;
    }

    /**
     * 关闭连接
     */
    public static void close(ChannelSftp channelSftp) {

        if (channelSftp != null) {
            if (channelSftp.isConnected()) {
                channelSftp.disconnect();
            } else if (channelSftp.isClosed()) {
                logger.info("sftp is closed already");
            }
            try {
                if (null != channelSftp.getSession()) {
                    channelSftp.getSession().disconnect();
                }
            } catch (JSchException e) {
                logger.error("根据channel获取session并关闭session时出错", e);
            }
        }
    }

    /**
     * 上传文件
     */
    public static boolean uploadFile() {
        return false;
    }

    /**
     * 下载文件
     */
    public static boolean downloadFiles(ChannelSftp channelSftp, String remotePath, String localPath, int recursion) {

        Object filesInRemotePath = null;
        String workDir = remotePath.substring(0, remotePath.lastIndexOf("/"));
        String fileReg = remotePath.substring(remotePath.lastIndexOf("/"));

        try {
            filesInRemotePath = channelSftp.ls(remotePath);
        } catch (SftpException e) {
            logger.error("remotePath下执行ls命令出错", e);
        }

        //判断是否为文件
        if (filesInRemotePath instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) filesInRemotePath;
            //如果不是目录
            if (!entry.getAttrs().isDir()) {
                try {
                    channelSftp.cd(workDir);
                } catch (SftpException e) {
                    logger.error("移到工作目录出现错误", e);
                    return false;
                }
                logger.info("Change path to {}", workDir);

                //正则匹配文件，遍历下载
                for (String fileName : entry) {


            }



        }





            File file = new File(localPath);

            try {
                OutputStream outputStream = new FileOutputStream(file);

                channelSftp.get(remotePath, outputStream);
                logger.info("文件下载成功-----  {}", file.getName());
                return true;
            } catch (FileNotFoundException e) {
                logger.error("获取文件输出流出错", e);
                return false;
            } catch (SftpException e) {
                logger.error("get文件出错", e);
                return false;
            }
        }


    }

    /**
     * 删除文件
     */

    public static boolean deleteFiles() {

    }


}
