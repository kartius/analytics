package com.example.analytics.model.coinMarketCap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoPriceResponse {
    @JsonProperty("status")
    private Status status;

    @JsonProperty("data")
    private CryptoData data;


    @Override
    public String toString() {
        return "CryptoPriceResponse{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
