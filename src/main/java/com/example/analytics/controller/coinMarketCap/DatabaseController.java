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

    @GetMapping("/by-symbol/{symbol}")
    public List<CryptoCurrencyData> getCryptoDataBySymbol(@PathVariable String symbol) {
        return dataBaseService.getCryptoDataBySymbol(symbol);
    }

    @GetMapping("/by-currency/{currency}")
    public List<CryptoCurrencyData> getCryptoDataByCurrency(@PathVariable String currency) {
        return dataBaseService.getCryptoDataByCurrency(currency);
    }

    @GetMapping("/by-symbol-and-currency/{symbol}/{currency}")
    public List<CryptoCurrencyData> getCryptoDataBySymbolAndCurrency(@PathVariable String symbol, @PathVariable String currency) {
        return dataBaseService.getCryptoDataBySymbolAndCurrency(symbol, currency);
    }
}
