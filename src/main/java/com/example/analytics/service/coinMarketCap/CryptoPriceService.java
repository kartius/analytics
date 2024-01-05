package com.example.analytics.service.coinMarketCap;

import com.example.analytics.exception.coinMarketCap.ApiException;
import com.example.analytics.model.coinMarketCap.CryptoPriceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static com.example.analytics.utils.PropertiesReader.getProperty;
import static java.util.Locale.US;

@Service
public class CryptoPriceService {
    @Value("${crypto.api.key}")
    private String apiKey;

    @Value("${crypto.base.url}")
    private String baseUrl;

    @Value("${crypto.symbol}")
    private String cryptocurrency;

    @Value("${crypto.convert.currency}")
    private String currency;

    private final RestTemplate restTemplate;

    public CryptoPriceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchAndFormatCryptoPrice() {
        baseUrl = getProperty("crypto.base.url");
        cryptocurrency = getProperty("crypto.symbol").toUpperCase();
        currency = getProperty("crypto.convert.currency").toUpperCase();
        apiKey = getProperty("crypto.api.key");
        String url = String.format("%s?symbol=%s&convert=%s&CMC_PRO_API_KEY=%s", baseUrl, cryptocurrency, currency, apiKey);
        CryptoPriceResponse response = restTemplate.getForObject(url, CryptoPriceResponse.class);

        try {
            if (response != null && response.getStatus().getErrorCode() == 0) {
                double price;
                String formattedPrice, cryptocurrencyName;
                switch (currency) {
                    case "USD":
                        switch (cryptocurrency) {
                            case "BTC":
                                price = response.getData().getBtcInfo().getQuote().getUsdInfo().getPrice();
                                formattedPrice = formatPrice(price);
                                cryptocurrencyName = "BTC";
                                break;
                            case "ETH":
                                price = response.getData().getEthInfo().getQuote().getUsdInfo().getPrice();
                                formattedPrice = formatPrice(price);
                                cryptocurrencyName = "ETH";
                                break;
                            case "SOL":
                                price = response.getData().getSolInfo().getQuote().getUsdInfo().getPrice();
                                formattedPrice = formatPrice(price);
                                cryptocurrencyName = "SOL";
                                break;
                            default:
                                return "Unsupported cryptocurrency: " + cryptocurrency;
                        }
                        break;
                    case "UAH":
                        switch (cryptocurrency) {
                            case "BTC":
                                price = response.getData().getBtcInfo().getQuote().getUahInfo().getPrice();
                                formattedPrice = formatPrice(price);
                                cryptocurrencyName = "BTC";
                                break;
                            case "ETH":
                                price = response.getData().getEthInfo().getQuote().getUahInfo().getPrice();
                                formattedPrice = formatPrice(price);
                                cryptocurrencyName = "ETH";
                                break;
                            case "SOL":
                                price = response.getData().getSolInfo().getQuote().getUahInfo().getPrice();
                                formattedPrice = formatPrice(price);
                                cryptocurrencyName = "SOL";
                                break;
                            default:
                                return "Unsupported cryptocurrency: " + cryptocurrency;
                        }
                        break;
                    default:
                        String errorMessage = response.getStatus().getErrorMessage();
                        return "Error: " + errorMessage;
                }
                return String.format("Current price of %s: %s %s", cryptocurrencyName, formattedPrice, currency);
            } else
                throw new ApiException("Error: " + (response != null ? response.getStatus().getErrorMessage() : "Unknown error"));
        } catch (Exception e) {
            throw new ApiException("Error while fetching crypto price", e);
        }
    }

    private String formatPrice(double price) {
        return new DecimalFormat("#,##0.00", new DecimalFormatSymbols(US)).format(price);
    }
}
