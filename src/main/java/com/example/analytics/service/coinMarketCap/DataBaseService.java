package com.example.analytics.service.coinMarketCap;

import com.example.analytics.model.coinMarketCap.CryptoCurrencyData;
import com.example.analytics.repository.coinMarketCap.CryptoCurrencyDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataBaseService {
    private final CryptoCurrencyDataRepository cryptoCurrencyDataRepository;

    public DataBaseService(CryptoCurrencyDataRepository cryptoCurrencyDataRepository) {
        this.cryptoCurrencyDataRepository = cryptoCurrencyDataRepository;
    }

    public List<CryptoCurrencyData> getCryptoDataBySymbol(String symbol) {
        return cryptoCurrencyDataRepository.findBySymbol(symbol);
    }

    public List<CryptoCurrencyData> getCryptoDataByCurrency(String currency) {
        return cryptoCurrencyDataRepository.findByCurrency(currency);
    }

    public List<CryptoCurrencyData> getCryptoDataBySymbolAndCurrency(String symbol, String currency) {
        return cryptoCurrencyDataRepository.findBySymbolAndCurrency(symbol, currency);
    }
}
