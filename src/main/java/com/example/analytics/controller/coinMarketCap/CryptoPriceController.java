package com.example.analytics.controller.coinMarketCap;

import com.example.analytics.service.coinMarketCap.CryptoPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crypto/price")
public class CryptoPriceController {
    private final CryptoPriceService cryptoPriceService;

    public CryptoPriceController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    @GetMapping("/{ticker}/{convert}")
    public String getCryptoPrice(@PathVariable String ticker, @PathVariable String convert) {
        return cryptoPriceService.getCryptoPrice(ticker, convert);
    }
}