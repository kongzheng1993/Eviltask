package com.cmos.bj.ngtask.repository;

import com.cmos.bj.ngtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/11/8 17:41
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User getByUserNameAndPassword(String username, String password);

}
