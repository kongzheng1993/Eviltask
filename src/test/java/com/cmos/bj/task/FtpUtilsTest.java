package com.cmos.bj.task;

import com.cmos.bj.ngtask.utils.FtpUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.hibernate.result.Output;
import org.junit.Test;

import java.io.*;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/11 17:58
 */
public class FtpUtilsTest {

    @Test
    public void Test() {

        File localFile = new File("Data/10.10.10.1/test.txt");

        File file = new File("Data/10.10.10.1");

        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            OutputStream out = new FileOutputStream(localFile);
            String str = "fuck off";
            out.write(str.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
