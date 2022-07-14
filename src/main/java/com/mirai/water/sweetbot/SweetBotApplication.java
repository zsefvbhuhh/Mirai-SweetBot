package com.mirai.water.sweetbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource(value = "classpath:SweetBot.properties",encoding = "UTF-8")
public class SweetBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(SweetBotApplication.class, args);
    }
}
