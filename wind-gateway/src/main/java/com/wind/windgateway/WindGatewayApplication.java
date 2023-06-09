package com.wind.windgateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

// 网关不配置数据源
@SpringBootApplication(scanBasePackages = "com.wind")
@EnableDubbo
@Service
public class WindGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindGatewayApplication.class, args);
    }

}
