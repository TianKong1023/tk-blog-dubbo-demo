package com.kk.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
@MapperScan("com.kk.blog.mapper")
public class DemoApplication {

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
        System.out.println(Timestamp.valueOf(LocalDateTime.now()));
        SpringApplication.run(DemoApplication.class, args);
    }

}
