package com.qst.crop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.qst.crop.dao")
public class RongXiaoTongApplication {
    public static void main(String[] args) {
        SpringApplication.run(RongXiaoTongApplication.class,args);
    }
}
