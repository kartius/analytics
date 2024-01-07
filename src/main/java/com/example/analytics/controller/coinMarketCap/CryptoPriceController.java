package com.example.analytics.controller.coinMarketCap;

import com.example.analytics.exception.coinMarketCap.ApiException;
import com.example.analytics.service.coinMarketCap.CryptoPriceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/crypto/price")
public class CryptoPriceController {
    private final CryptoPriceService cryptoPriceService;

    public CryptoPriceController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    @GetMapping("/{symbol}/{convert}")
    public String getCryptoPrice(@PathVariable String symbol, @PathVariable String convert) {
        String apiResponse = cryptoPriceService.fetchCryptoPrice(symbol, convert);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(apiResponse);
            JsonNode dataNode = rootNode.path("data");

            if (dataNode.isObject()) {
                JsonNode symbolNode = dataNode.path(symbol.toUpperCase());
                if (symbolNode.isObject()) {
                    JsonNode quoteNode = symbolNode.path("quote");
                    if (quoteNode.isObject()) {
                        JsonNode currencyNode = quoteNode.path(convert.toUpperCase());
                        if (currencyNode.isObject()) {
                            double price = currencyNode.path("price").asDouble();
                            return String.format("Current price of %s: %.2f %s",
                                    symbol.toUpperCase(),
                                    price, convert.toUpperCase());
                        }
                    }
                }
            }
        } catch (ApiException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return "Failed to retrieve crypto price information.";
    }
}