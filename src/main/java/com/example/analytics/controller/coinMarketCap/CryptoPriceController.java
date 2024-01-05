package com.example.analytics.controller.coinMarketCap;

import com.example.analytics.service.coinMarketCap.CryptoPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crypto")
public class CryptoPriceController {
    private final CryptoPriceService cryptoPriceService;

    @Autowired
    public CryptoPriceController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    @GetMapping("/price")
    public String getCryptoPrice() {
        return cryptoPriceService.fetchAndFormatCryptoPrice();
    }
}