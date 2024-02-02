package com.example.analytics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GoogleTrendsScraper {

    private static final Logger log = LoggerFactory.getLogger(GoogleTrendsScraper.class);

    public static String getGoogleTrendsData(String keyword, String country) throws IOException {
        String url = "https://trends.google.com/trends/explore?q=" + keyword + "&geo=" + country;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("Error connecting to Google Trends", e);
            throw new IOException("Error connecting to Google Trends", e);
        }

        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();

            Element trendElement = document.selectFirst("div.chart-png");

            if (trendElement != null) {
                return trendElement.attr("src");
            } else {
                return "No trends data found";
            }
        } catch (IOException e) {
            log.error("Error connecting to Google Trends", e);
            throw new IOException("Error connecting to Google Trends", e);
        }
    }

    public static void main(String[] args) {
        try {
            String trendsData = getGoogleTrendsData("crypto", "US");
            System.out.println(trendsData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
