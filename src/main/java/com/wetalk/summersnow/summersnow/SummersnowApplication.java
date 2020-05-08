package com.wetalk.summersnow.summersnow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wetalk.summersnow.summersnow.mapper")
public class SummersnowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SummersnowApplication.class, args);
    }

}
