package com.cmos.bj.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DruidConfig {

    @ConfigurationProperties("spring")
    public DataSource DataSource(DruidProperties druidProperties) {
        return null;
    }

}
