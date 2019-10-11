package com.cmos.bj.ngtask.repository;

import com.cmos.bj.ngtask.model.SpringSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 11:28
 */

public interface SpringScheduleRepository extends JpaRepository<SpringSchedule, Integer> {

    /**
     * 根据类名查找定时任务
     *
     * @param cronClassName 类名
     * @return springSchedule   定时任务
     */
    SpringSchedule findByCronClassName(String cronClassName);


}
