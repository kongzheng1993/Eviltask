package com.cmos.bj.ngtask.task;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 12:45
 */
public interface ScheduleOfTask extends Runnable {


    /**
     * 定时任务业务逻辑
     */
    void execute();

    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {

        execute();

    }


}
