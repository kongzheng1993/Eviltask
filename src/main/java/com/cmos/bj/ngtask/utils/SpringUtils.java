package com.cmos.bj.ngtask.utils;

import org.springframework.context.ApplicationContext;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 12:55
 */
public class SpringUtils {


    private static ApplicationContext applicationContext;

    public static Object getBean(Class clazz) {
        return applicationContext.getBean(clazz);
    }

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

}
