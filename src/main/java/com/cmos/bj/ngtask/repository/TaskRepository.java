package com.cmos.bj.ngtask.repository;

import com.cmos.bj.ngtask.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 11:28
 */

public interface TaskRepository extends JpaRepository<Task, Integer> {

    /**
     * 获取所有task
     * @param pageable
     * @return
     */
    @Override
    Page<Task> findAll(Pageable pageable);

}
