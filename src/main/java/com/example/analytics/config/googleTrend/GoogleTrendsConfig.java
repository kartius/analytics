package com.example.analytics.config.googleTrend;

import org.freaknet.gtrends.api.GoogleAuthenticator;
import org.freaknet.gtrends.api.GoogleTrendsClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class GoogleTrendsConfig {

    @Value("${google.api.username}")
    private String username;

    @Value("${google.api.password}")
    private String password;

    @Bean
    public GoogleTrendsClient googleTrendsClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        GoogleAuthenticator authenticator = new GoogleAuthenticator(username, password, httpClient);
        return new GoogleTrendsClient(authenticator, httpClient);
    }
}