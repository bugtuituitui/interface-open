package com.wind.web;

import com.wind.web.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wind.*")
@EnableDubbo
public class WebApplication {

    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    void hello() {
        demoService.say();
    }
}
