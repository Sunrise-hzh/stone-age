package com.sunrise.stoneage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.sunrise.stoneage.dao","com.sunrise.stoneage.mbg.mapper"})
public class StoneageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoneageApplication.class, args);
    }

}
