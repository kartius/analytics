package com.example.analytics.model.coinMarketCap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyInfo {
    @JsonProperty("price")
    private double price;

    @JsonProperty("volume_24h")
    private double volume24h;

    @JsonProperty("volume_change_24h")
    private double volumeChange24h;

    @JsonProperty("percent_change_1h")
    private double percentChange1h;

    @JsonProperty("percent_change_24h")
    private double percentChange24h;

    @JsonProperty("percent_change_7d")
    private double percentChange7d;

    @JsonProperty("percent_change_30d")
    private double percentChange30d;

    @JsonProperty("percent_change_60d")
    private double percentChange60d;

    @JsonProperty("percent_change_90d")
    private double percentChange90d;

    @JsonProperty("market_cap")
    private double marketCap;

    @JsonProperty("market_cap_dominance")
    private double marketCapDominance;

    @JsonProperty("fully_diluted_market_cap")
    private double fullyDilutedMarketCap;

    @JsonProperty("tvl")
    private String tvl;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @Override
    public String toString() {
        return "CurrencyInfo{" +
                "price=" + price +
                ", volume24h=" + volume24h +
                ", volumeChange24h=" + volumeChange24h +
                ", percentChange1h=" + percentChange1h +
                ", percentChange24h=" + percentChange24h +
                ", percentChange7d=" + percentChange7d +
                ", percentChange30d=" + percentChange30d +
                ", percentChange60d=" + percentChange60d +
                ", percentChange90d=" + percentChange90d +
                ", marketCap=" + marketCap +
                ", marketCapDominance=" + marketCapDominance +
                ", fullyDilutedMarketCap=" + fullyDilutedMarketCap +
                ", tvl='" + tvl + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}
