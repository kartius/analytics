package com.example.analytics.repository.coinMarketCap;

import com.example.analytics.model.coinMarketCap.CryptoCurrencyData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CryptoCurrencyDataRepository extends JpaRepository<CryptoCurrencyData, Long> {
    List<CryptoCurrencyData> findBySymbol(String symbol);
    List<CryptoCurrencyData> findByCurrency(String currency);
    List<CryptoCurrencyData> findBySymbolAndCurrency(String symbol, String currency);
}
