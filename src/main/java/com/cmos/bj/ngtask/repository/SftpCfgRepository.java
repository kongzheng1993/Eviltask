package com.cmos.bj.ngtask.repository;

import com.cmos.bj.ngtask.model.SftpCfg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 11:28
 */

@Repository
public interface SftpCfgRepository extends JpaRepository<SftpCfg, Integer> {



}
