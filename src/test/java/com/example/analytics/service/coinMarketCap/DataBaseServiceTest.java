package com.example.analytics.service.coinMarketCap;

import com.example.analytics.model.coinMarketCap.CryptoCurrencyData;
import com.example.analytics.repository.coinMarketCap.CryptoCurrencyDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataBaseServiceTest {
    @Mock
    private CryptoCurrencyDataRepository currencyDataRepository;

    @InjectMocks
    private DataBaseService dataBaseService;

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
    @DisplayName("Finding data by ticker using service")
    public void getCryptoDataByTickerTest() {
        String ticker = data.getTicker();
        when(currencyDataRepository.findByTicker(ticker)).thenReturn(List.of(data));

        List<CryptoCurrencyData> serviceDataByTicker = dataBaseService.getCryptoDataByTicker(ticker);
        assertThat(serviceDataByTicker).isNotNull();

        verify(currencyDataRepository, times(1)).findByTicker(ticker);
        assertThat(serviceDataByTicker.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Finding data by currency using service")
    public void getCryptoDataByCurrencyTest() {
        String currency = data.getCurrency();
        when(currencyDataRepository.findByCurrency(currency)).thenReturn(List.of(data));

        List<CryptoCurrencyData> serviceDataByCurrency = dataBaseService.getCryptoDataByCurrency(currency);
        assertThat(serviceDataByCurrency).isNotNull();

        verify(currencyDataRepository, times(1)).findByCurrency(currency);
        assertThat(serviceDataByCurrency.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Finding data by ticker & currency using service")
    public void getCryptoDataByTickerAndCurrencyTest() {
        String ticker = data.getTicker();
        String currency = data.getCurrency();
        when(currencyDataRepository.findByTickerAndCurrency(ticker, currency)).thenReturn(List.of(data));

        List<CryptoCurrencyData> serviceDataByTickerAndCurrency = dataBaseService.getCryptoDataByTickerAndCurrency(ticker, currency);
        assertThat(serviceDataByTickerAndCurrency).isNotNull();

        verify(currencyDataRepository, times(1)).findByTickerAndCurrency(ticker, currency);
        assertThat(serviceDataByTickerAndCurrency.size()).isEqualTo(1);
    }
}
