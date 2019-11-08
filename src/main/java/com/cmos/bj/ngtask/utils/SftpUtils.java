package com.cmos.bj.ngtask.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Pattern;

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

        Assert.isTrue(session != null, "sftp session 为空！！！");

        session.setPassword(password);

        session.setConfig("PreferredAuthentications", "password,gssapi-with-mic,publickey,keyboard-interactive");
        session.setConfig("StrictHostKeyChecking", "no");

        try {
            session.setTimeout(30000);
        } catch (JSchException e) {
            logger.error("设置超时时间出现错误", e);
        }

        try {
            session.connect();
            logger.info("sftp session 连接成功");
        } catch (JSchException e) {
            logger.error("sftp session 连接失败", e);
        }

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
                logger.info("channelSftp断开连接成功");
            } else if (channelSftp.isClosed()) {
                logger.info("sftp is closed already");
            }
            try {
                if (null != channelSftp.getSession()) {
                    channelSftp.getSession().disconnect();
                    logger.info("sftp session断开成功");
                }
            } catch (JSchException e) {
                logger.error("根据channel获取session并关闭session时出错", e);
            }
        }
    }

    /**
     * 上传文件
     */
    public static boolean uploadFile(ChannelSftp channelSftp, String remotePath, InputStream inputStream) {


        try {
            channelSftp.put(inputStream, remotePath);
            return true;
        } catch (SftpException e) {
            logger.error("上传文件出错: " + remotePath, e);
            return false;
        }
    }

    /**
     * 下载文件
     */
    public static boolean downloadFiles(ChannelSftp channelSftp, String remotePath, String localPath,String encoding, int recursion) {

        Object filesInRemotePath = null;
        String workDir = getWorkDir(remotePath);
        String fileReg = getFileReg(remotePath);

        try {
            Class channelSftpClass = ChannelSftp.class;
            Field serverVersion = channelSftpClass.getDeclaredField("server_version");
            serverVersion.setAccessible(true);
            serverVersion.set(channelSftp, 2);
            channelSftp.setFilenameEncoding(encoding);
            filesInRemotePath = channelSftp.ls(remotePath);
        } catch (SftpException e) {
            logger.error("remotePath下执行ls命令出错", e);
        } catch (NoSuchFieldException e) {
            logger.error("修改sftp服务器version，获取channelSftp的server_version字段时出错", e);
        } catch (IllegalAccessException e) {
            logger.error("向channelSftp对象中setServerVersion为2时出错", e);
        }

        //遍历目录下所有文件和目录
        Vector<ChannelSftp.LsEntry> entry = (Vector<ChannelSftp.LsEntry>) filesInRemotePath;

        if (entry != null && entry.size() > 0) {
            for (ChannelSftp.LsEntry file : entry) {
                if (file.getAttrs().isDir()) {
                    if (recursion == 1) {
                        downloadFiles(channelSftp, workDir + "/" + file.getFilename() + "/" + fileReg, (localPath.endsWith("/") ? localPath : localPath +"/") + file.getFilename(),encoding, recursion);
                    }
                } else {

                    File localFileDir = new File(localPath);
                    if (!localFileDir.exists()) {
                        localFileDir.mkdirs();
                    }

                    File localFile = new File((localPath.endsWith("/") ? localPath : localPath + "/") + file.getFilename());

                    try {
                        OutputStream outputStream = new FileOutputStream(localFile);
                        channelSftp.get(workDir + "/" + file.getFilename(), outputStream);
                        logger.info("文件下载成功-----  {}", localFile.getAbsolutePath());
                    } catch (FileNotFoundException e) {
                        logger.error("获取文件输出流出错 " + (localPath.endsWith("/") ? localPath : localPath + "/") + file.getFilename(), e);
                        return false;
                    } catch (SftpException e) {
                        logger.error("get文件出错 " + workDir + "/" + file.getFilename(), e);
                        return false;
                    }
                }

            }
            return true;
        } else {
            logger.info("指定目录下没有指定文件 " + remotePath);
            return false;
        }

    }

    /**
     * 删除文件
     */
    public static boolean deleteFiles(ChannelSftp channelSftp, String remotePath, int recursion) {

        Object filesInRemotePath = null;
        String workDir = getWorkDir(remotePath);
        String fileReg = getFileReg(remotePath);

        try {
            filesInRemotePath = channelSftp.ls(remotePath);
        } catch (SftpException e) {
            logger.error("remotePath下执行ls命令出错", e);
        }

        //遍历目录下所有文件和目录
        if (filesInRemotePath instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
            Vector<ChannelSftp.LsEntry> entry = (Vector<ChannelSftp.LsEntry>) filesInRemotePath;

            if (entry != null && entry.size() > 0) {
                for (ChannelSftp.LsEntry file : entry) {
                    if (Pattern.matches(fileReg, file.getFilename())) {
                        if (file.getAttrs().isDir()) {
                            if (recursion == 1) {
                                deleteFiles(channelSftp, workDir + file.getFilename() + "/" + fileReg, recursion);
                            }
                        } else {
                            try {
                                channelSftp.rm(file.getFilename());
                            } catch (SftpException e) {
                                logger.error("删除文件失败：" + file.getFilename(), e);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static String getWorkDir(String remotePath) {
        return remotePath.substring(0, remotePath.lastIndexOf("/"));
    }

    private static String getFileReg(String remotePath) {
        return remotePath.substring(remotePath.lastIndexOf("/"));
    }

}
