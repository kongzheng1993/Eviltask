package com.cmos.bj.ngtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author kongz
 */
@EnableScheduling
@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(TaskApplication.class, args);
        //SpringUtils.setApplicationContext(ac);
	}

}
