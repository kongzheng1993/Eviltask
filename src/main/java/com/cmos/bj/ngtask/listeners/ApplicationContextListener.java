package com.cmos.bj.ngtask.listeners;

import com.cmos.bj.ngtask.utils.SpringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 17:01
 */
@Component
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        SpringUtils.setApplicationContext(contextRefreshedEvent.getApplicationContext());
    }
}
