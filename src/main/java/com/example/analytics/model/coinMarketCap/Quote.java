package com.example.analytics.model.coinMarketCap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    @JsonProperty("USD")
    private CurrencyInfo usdInfo;

    @JsonProperty("UAH")
    private CurrencyInfo uahInfo;

    @Override
    public String toString() {
        return "Quote{" +
                "usdInfo=" + usdInfo +
                ", uahInfo=" + uahInfo +
                '}';
    }
}
