package com.example.analytics.controller.coinMarketCap;

import com.example.analytics.model.coinMarketCap.CryptoCurrencyData;
import com.example.analytics.service.coinMarketCap.DataBaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/database")
public class DatabaseController {
    private final DataBaseService dataBaseService;

    public DatabaseController(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    @GetMapping("/by-ticker/{ticker}")
    public List<CryptoCurrencyData> getCryptoDataByTicker(@PathVariable String ticker) {
        return dataBaseService.getCryptoDataByTicker(ticker);
    }

    @GetMapping("/by-currency/{currency}")
    public List<CryptoCurrencyData> getCryptoDataByCurrency(@PathVariable String currency) {
        return dataBaseService.getCryptoDataByCurrency(currency);
    }

    @GetMapping("/by-ticker-and-currency/{ticker}/{currency}")
    public List<CryptoCurrencyData> getCryptoDataByTickerAndCurrency(@PathVariable String ticker, @PathVariable String currency) {
        return dataBaseService.getCryptoDataByTickerAndCurrency(ticker, currency);
    }
}
