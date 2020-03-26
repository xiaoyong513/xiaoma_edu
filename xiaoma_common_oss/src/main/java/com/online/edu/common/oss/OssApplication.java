package com.online.edu.common.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: changyong
 * @Date: create in 14:36 2020/3/15
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.online.edu.common", "com.online.edu.common.oss"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
