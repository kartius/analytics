package com.example.analytics.model.coinMarketCap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCurrencyInfo {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("num_market_pairs")
    private int numMarketPairs;

    @JsonProperty("date_added")
    private String dateAdded;

    @JsonProperty("tags")
    private String[] tags;

    @JsonProperty("max_supply")
    private long maxSupply;

    @JsonProperty("circulating_supply")
    private double circulatingSupply;

    @JsonProperty("total_supply")
    private long totalSupply;

    @JsonProperty("is_active")
    private int isActive;

    @JsonProperty("infinite_supply")
    private boolean infiniteSupply;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("cmc_rank")
    private int cmcRank;

    @JsonProperty("is_fiat")
    private int isFiat;

    @JsonProperty("self_reported_circulating_supply")
    private String selfReportedCirculatingSupply;

    @JsonProperty("self_reported_market_cap")
    private String selfReportedMarketCap;

    @JsonProperty("tvl_ratio")
    private String tvlRatio;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("quote")
    private Quote quote;

    @Override
    public String toString() {
        return "CryptoCurrencyInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", slug='" + slug + '\'' +
                ", numMarketPairs=" + numMarketPairs +
                ", dateAdded='" + dateAdded + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", maxSupply=" + maxSupply +
                ", circulatingSupply=" + circulatingSupply +
                ", totalSupply=" + totalSupply +
                ", isActive=" + isActive +
                ", infiniteSupply=" + infiniteSupply +
                ", platform='" + platform + '\'' +
                ", cmcRank=" + cmcRank +
                ", isFiat=" + isFiat +
                ", selfReportedCirculatingSupply='" + selfReportedCirculatingSupply + '\'' +
                ", selfReportedMarketCap='" + selfReportedMarketCap + '\'' +
                ", tvlRatio='" + tvlRatio + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", quote=" + quote +
                '}';
    }
}
