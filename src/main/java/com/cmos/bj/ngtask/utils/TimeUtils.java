package com.cmos.bj.ngtask.utils;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/24 9:27
 */
public class TimeUtils {

    public static String getNowTimeStr(String timeReg) {

        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timeReg);

        return dateTime.format(dateTimeFormatter);
    }

}
