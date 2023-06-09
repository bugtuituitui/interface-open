package com.wind.portal;

import com.wind.portal.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wind.*")
@EnableDubbo
public class PortalApplication {

    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

    void hello() {
        demoService.say();
    }
}
