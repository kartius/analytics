package com.example.analytics.model.googleTrend;

import org.freaknet.gtrends.api.GoogleTrendsRequest;
import org.freaknet.gtrends.api.exceptions.GoogleTrendsRequestException;

public class TrendsRequest extends GoogleTrendsRequest {

    private String keyword;

    public TrendsRequest(String keyword) throws GoogleTrendsRequestException {
        super(String.valueOf(System.currentTimeMillis())); // Use the current time in milliseconds
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}