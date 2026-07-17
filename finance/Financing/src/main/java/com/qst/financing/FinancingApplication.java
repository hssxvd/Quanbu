package com.qst.financing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.qst.financing.dao")
public class FinancingApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinancingApplication.class,args);
    }
}
