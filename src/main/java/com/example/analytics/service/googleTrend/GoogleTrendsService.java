package com.example.analytics.service.googleTrend;

import com.example.analytics.model.googleTrend.TrendData;
import com.example.analytics.repository.googleTrend.TrendDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GoogleTrendsService {

    @Value("${google.api.key}")
    private String apiKey;

    private final TrendDataRepository trendDataRepository;
    private final RestTemplate restTemplate;

    public GoogleTrendsService(TrendDataRepository trendDataRepository, RestTemplate restTemplate) {
        this.trendDataRepository = trendDataRepository;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 3600000) // 3600000 ms = 1 hour
    public void updateTrendsDataPeriodically() {
        // Update trends automatically every hour
        // Call your getTrendsData method here with the required parameters
    }

    public String getTrendsData(String keyword, String region, String country, String apiUrl) throws IOException {
        String requestUrl = apiUrl + "?key=" + apiKey + "&geo=" + country + "&date=202101&cat=all";
        requestUrl += "&q=" + keyword;
        requestUrl += "&time=now 1-d";

        // Perform a request to the Google Trends API
        String trendData = restTemplate.getForObject(requestUrl, String.class);

        // Save the data to the database
        saveTrendDataToDatabase(keyword, region, country, trendData);

        return trendData;
    }

    private void saveTrendDataToDatabase(String keyword, String region, String country, String trendData) {
        TrendData trendDataEntity = new TrendData();
        trendDataEntity.setKeyword(keyword);
        trendDataEntity.setRegion(region);
        trendDataEntity.setCountry(country);
        trendDataEntity.setTrendData(trendData);

        trendDataRepository.save(trendDataEntity);
    }
}