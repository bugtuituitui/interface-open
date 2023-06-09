package com.wind.windgateway;

import org.springframework.stereotype.Service;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

// 网关不配置数据源
@SpringBootApplication(scanBasePackages = "com.wind")
@EnableDubbo
@Service
public class WindGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindGatewayApplication.class, args);
    }

}
