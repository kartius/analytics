package com.example.analytics.repository;

import com.example.analytics.model.coinMarketCap.CryptoCurrencyData;
import com.example.analytics.repository.coinMarketCap.CryptoCurrencyDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CryptoCurrencyDataRepositoryTest {
    @Autowired
    private CryptoCurrencyDataRepository currencyDataRepository;

    private CryptoCurrencyData data;

    @BeforeEach
    public void setup() {
        data = CryptoCurrencyData.builder()
                .id(1L)
                .ticker("BTC")
                .price(4435D)
                .currency("USD").build();
        currencyDataRepository.save(data);
    }

    @Test
    @DisplayName("Finding data by ticker")
    public void findByTickerTest() {
        String ticker = data.getTicker();
        List<CryptoCurrencyData> dataByTicker = currencyDataRepository.findByTicker(ticker);
        assertThat(dataByTicker).isNotNull();
        assertThat(dataByTicker.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Finding data by currency")
    public void findByCurrencyTest() {
        String currency = data.getCurrency();
        List<CryptoCurrencyData> dataByCurrency = currencyDataRepository.findByCurrency(currency);
        assertThat(dataByCurrency).isNotNull();
        assertThat(dataByCurrency.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Finding data by currency & ticker")
    public void findByTickerAndCurrencyTest() {
        String currency = data.getCurrency();
        String ticker = data.getTicker();
        List<CryptoCurrencyData> dataByTickerAndCurrency = currencyDataRepository.findByTickerAndCurrency(ticker, currency);
        assertThat(dataByTickerAndCurrency).isNotNull();
        assertThat(dataByTickerAndCurrency.size()).isEqualTo(1);
    }
}
