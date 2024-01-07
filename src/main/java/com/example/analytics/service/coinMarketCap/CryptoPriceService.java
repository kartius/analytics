package com.example.analytics.service.coinMarketCap;

import com.example.analytics.exception.coinMarketCap.ApiException;
import com.example.analytics.model.coinMarketCap.CryptoCurrencyData;
import com.example.analytics.repository.coinMarketCap.CryptoCurrencyDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static com.example.analytics.utils.PropertiesReader.getProperty;

@Service
public class CryptoPriceService {
    private final String apiUrl = getProperty("crypto.base.url");
    private final String apiKey = getProperty("crypto.api.key");
    private final RestTemplate restTemplate;
    private final CryptoCurrencyDataRepository cryptoCurrencyDataRepository;

    public CryptoPriceService(RestTemplate restTemplate, CryptoCurrencyDataRepository cryptoCurrencyDataRepository) {
        this.restTemplate = restTemplate;
        this.cryptoCurrencyDataRepository = cryptoCurrencyDataRepository;
    }

    public String fetchCryptoPrice(String symbol, String convert) {
        String url = String.format("%s?symbol=%s&convert=%s&CMC_PRO_API_KEY=%s", apiUrl, symbol, convert, apiKey);
        String apiResponse = restTemplate.getForObject(url, String.class);

        double yourObtainedPrice;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(apiResponse);
            JsonNode dataNode = rootNode.path("data");

            JsonNode symbolNode = dataNode.path(symbol.toUpperCase());
            JsonNode quoteNode = symbolNode.path("quote");
            JsonNode currencyNode = quoteNode.path(convert.toUpperCase());
            yourObtainedPrice = currencyNode.path("price").asDouble();
        } catch (ApiException | IOException e) {
            System.err.println("Error: " + e.getMessage());
            return "Failed to retrieve crypto price information.";
        }

        // Save crypto currency data to database
        CryptoCurrencyData cryptoCurrencyData = new CryptoCurrencyData();
        cryptoCurrencyData.setSymbol(symbol);
        cryptoCurrencyData.setPrice(yourObtainedPrice);
        cryptoCurrencyData.setCurrency(convert);
        cryptoCurrencyDataRepository.save(cryptoCurrencyData);

        return apiResponse;
    }
}
