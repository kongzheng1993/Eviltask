package com.cmos.bj.ngtask.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/24 9:27
 */
public class TimeUtils {

    public static String getNowTimeStr(String timeReg) {

        String plus = "+";
        String minus = "-";
        String realTimeReg = timeReg;
        String[] param;

        LocalDateTime dateTime = LocalDateTime.now();

        if (timeReg.contains(plus)) {
            param = timeReg.split(plus);
            realTimeReg = param[0];
            switch (realTimeReg) {
                case "yyyy":
                    dateTime = dateTime.plusYears(Long.parseLong(param[1]));
                    break;
                case "yyyyMM":
                    dateTime = dateTime.plusMonths(Long.parseLong(param[1]));
                    break;
                case "yyyyMMdd":
                    dateTime = dateTime.plusDays(Long.parseLong(param[1]));
                    break;
                case "yyyyMMddHH":
                    dateTime = dateTime.plusHours(Long.parseLong(param[1]));
                    break;
                case "yyyyMMddHHmm":
                    dateTime = dateTime.plusMinutes(Long.parseLong(param[1]));
                    break;
                default:
                    break;
            }
        }

        if (timeReg.contains(minus)) {
            param = timeReg.split(minus);
            realTimeReg = param[0];
            switch (realTimeReg) {
                case "yyyy":
                    dateTime = dateTime.minusYears(Long.parseLong(param[1]));
                    break;
                case "yyyyMM":
                    dateTime = dateTime.minusMonths(Long.parseLong(param[1]));
                    break;
                case "yyyyMMdd":
                    dateTime = dateTime.minusDays(Long.parseLong(param[1]));
                    break;
                case "yyyyMMddHH":
                    dateTime = dateTime.minusHours(Long.parseLong(param[1]));
                    break;
                case "yyyyMMddHHmm":
                    dateTime = dateTime.minusMinutes(Long.parseLong(param[1]));
                    break;
                default:
                    break;
            }
        }


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(realTimeReg);

        return dateTime.format(dateTimeFormatter);
    }

}
