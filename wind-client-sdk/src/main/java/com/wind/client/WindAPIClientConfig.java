package com.wind.client;

import com.wind.client.client.WindAPIClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author kfg
 * @date 2023/6/8 12:12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "windapi.client")
@ComponentScan
public class WindAPIClientConfig {

    private String key;

    private String secret;

    @Bean
    public WindAPIClient openApiClient() {
        return new WindAPIClient(key, secret);
    }
}
