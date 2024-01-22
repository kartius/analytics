package com.example.analytics.service.coinMarketCap;

import com.example.analytics.exception.coinMarketCap.ApiException;
import com.example.analytics.model.coinMarketCap.CryptoCurrencyData;
import com.example.analytics.repository.coinMarketCap.CryptoCurrencyDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class CryptoPriceService {

    @Value("${crypto.base.url}")
    private String apiUrl;

    @Value("${crypto.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final CryptoCurrencyDataRepository cryptoCurrencyDataRepository;

    public CryptoPriceService(RestTemplate restTemplate, CryptoCurrencyDataRepository cryptoCurrencyDataRepository) {
        this.restTemplate = restTemplate;
        this.cryptoCurrencyDataRepository = cryptoCurrencyDataRepository;
    }

    public CryptoCurrencyData fetchCryptoPrice(String ticker, String convert) {
        String url = String.format("%s?symbol=%s&convert=%s&CMC_PRO_API_KEY=%s", apiUrl, ticker, convert, apiKey);
        String apiResponse = restTemplate.getForObject(url, String.class);

        double yourObtainedPrice;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(apiResponse);
            JsonNode dataNode = rootNode.path("data");

            JsonNode symbolNode = dataNode.path(ticker.toUpperCase());
            JsonNode quoteNode = symbolNode.path("quote");
            JsonNode currencyNode = quoteNode.path(convert.toUpperCase());
            yourObtainedPrice = currencyNode.path("price").asDouble();
        } catch (ApiException | IOException e) {
            log.error("Error: {}",e.getMessage());
            return null;
        }

        // Save crypto currency data to database
        CryptoCurrencyData cryptoCurrencyData = new CryptoCurrencyData();
        cryptoCurrencyData.setTicker(ticker);
        cryptoCurrencyData.setPrice(yourObtainedPrice);
        cryptoCurrencyData.setCurrency(convert);
        cryptoCurrencyDataRepository.save(cryptoCurrencyData);

        return cryptoCurrencyData;
    }

    public String getCryptoPrice(String ticker, String convert) {
        CryptoCurrencyData cryptoCurrencyData = fetchCryptoPrice(ticker, convert);

        if (cryptoCurrencyData != null) {
            double price = cryptoCurrencyData.getPrice();
            return String.format("Current price of %s: %.2f %s",
                    ticker.toUpperCase(),
                    price, convert.toUpperCase());
        } else {
            return "Failed to retrieve crypto price information.";
        }
    }
}
