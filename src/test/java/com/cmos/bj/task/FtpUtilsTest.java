package com.cmos.bj.task;

import com.cmos.bj.ngtask.utils.TimeUtils;
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


        for (String a : "CREATEFILE:CSMS010{yyyyMMddHHmmss}.verf".split("\\|")) {
            System.out.println(a + "\n");
        }


    }

}
