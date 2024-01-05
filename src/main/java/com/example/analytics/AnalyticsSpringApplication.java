package com.example.analytics;

import com.example.analytics.service.coinMarketCap.CryptoPriceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AnalyticsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsSpringApplication.class, args);

        CryptoPriceService cryptoPriceService = new CryptoPriceService(new RestTemplate());
        System.out.println(cryptoPriceService.fetchAndFormatCryptoPrice());
    }
}
