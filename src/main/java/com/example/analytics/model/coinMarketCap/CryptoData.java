package com.example.analytics.model.coinMarketCap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoData {
    @JsonProperty("BTC")
    private CryptoCurrencyInfo btcInfo;

    @JsonProperty("ETH")
    private CryptoCurrencyInfo ethInfo;

    @JsonProperty("SOL")
    private CryptoCurrencyInfo solInfo;


    @Override
    public String toString() {
        return "CryptoData {" +
                "\n  btcInfo: " + (btcInfo != null ? btcInfo.toString() : "null") +
                "\n  ethInfo: " + (ethInfo != null ? ethInfo.toString() : "null") +
                "\n  solInfo: " + (solInfo != null ? solInfo.toString() : "null") +
                "\n}";
    }
}
