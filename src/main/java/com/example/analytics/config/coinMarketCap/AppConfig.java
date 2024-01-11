package com.example.analytics.config.coinMarketCap;

import com.example.analytics.service.coinMarketCap.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }
}
