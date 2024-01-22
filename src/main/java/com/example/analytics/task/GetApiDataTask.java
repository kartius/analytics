package com.example.analytics.task;

import com.example.analytics.service.coinMarketCap.CryptoPriceService;
import com.example.analytics.service.telegram.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetApiDataTask {

    private static final List<String> TICKERS = List.of("BTC", "USDT", "ETH");
    private static final List<String> CURRENCIES = List.of("USD", "EUR");

    private final CryptoPriceService cryptoPriceService;
    private final TelegramBotService telegramBotService;

    @Scheduled(cron = "0 0 4,8,16,20 * * *")
    public void getApiDataTask() {
        log.info("Starting task for get prices");

        TICKERS.forEach(t ->
                CURRENCIES.forEach(c -> telegramBotService.broadcast(cryptoPriceService.getCryptoPrice(t, c)))
        );
    }
}
