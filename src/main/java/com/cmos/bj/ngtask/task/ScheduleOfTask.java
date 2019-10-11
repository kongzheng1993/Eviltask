package com.cmos.bj.ngtask.task;

import com.cmos.bj.ngtask.enums.ScheduleStatusEnum;
import com.cmos.bj.ngtask.model.SpringSchedule;
import com.cmos.bj.ngtask.repository.SpringScheduleRepository;
import com.cmos.bj.ngtask.utils.SpringUtils;

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
        SpringScheduleRepository repository = (SpringScheduleRepository) SpringUtils.getBean(SpringScheduleRepository.class);
        SpringSchedule springSchedule = repository.findByCronClassName(this.getClass().getName());
        if (ScheduleStatusEnum.DISABLE.getCode().equals(springSchedule.getStatus())) {
            return;
        }
        execute();
    }


}
