package com.cmos.bj.task.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@EnableConfigurationProperties
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {

}
