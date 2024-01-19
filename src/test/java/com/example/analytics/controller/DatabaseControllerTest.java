package com.example.analytics.controller;

import com.example.analytics.model.coinMarketCap.CryptoCurrencyData;
import com.example.analytics.service.coinMarketCap.DataBaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DatabaseControllerTest {

    @MockBean
    private DataBaseService dataBaseService;

    @Autowired
    private TestRestTemplate restTemplate;

    private CryptoCurrencyData data;

    @BeforeEach
    public void setup() {
        data = CryptoCurrencyData.builder()
                .id(1L)
                .ticker("BTC")
                .price(4435D)
                .currency("USD").build();
    }

    @Test
    @DisplayName("Finding data using DataBaseController method 'getCryptoDataByTicker'")
    public void getCryptoDataByTickerTest() {
        String ticker = "BTC";
        when(dataBaseService.getCryptoDataByTicker(ticker)).thenReturn(List.of(data));
        ResponseEntity<List<CryptoCurrencyData>> response = restTemplate.exchange(
                "/database/by-ticker/{ticker}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                ticker
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getTicker()).isEqualTo(data.getTicker());

        verify(dataBaseService, times(1)).getCryptoDataByTicker(ticker);
    }

    @Test
    @DisplayName("Finding data using DataBaseController method 'getCryptoDataByCurrency'")
    public void getCryptoDataByCurrencyTest() {
        String currency = "USD";
        when(dataBaseService.getCryptoDataByCurrency(currency)).thenReturn(List.of(data));
        ResponseEntity<List<CryptoCurrencyData>> response = restTemplate.exchange(
                "/database/by-currency/{ticker}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                currency
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getCurrency()).isEqualTo(data.getCurrency());

        verify(dataBaseService, times(1)).getCryptoDataByCurrency(currency);
    }

    @Test
    @DisplayName("Finding data using DataBaseController method 'getCryptoDataByTickerAndCurrency'")
    public void getCryptoDataByTickerAndCurrencyTest() {
        String ticker = "BTC";
        String currency = "USD";
        when(dataBaseService.getCryptoDataByTickerAndCurrency(ticker, currency)).thenReturn(List.of(data));
        ResponseEntity<List<CryptoCurrencyData>> response = restTemplate.exchange(
                "/database/by-ticker-and-currency/{ticker}/{currency}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                ticker,
                currency
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getTicker()).isEqualTo(data.getTicker());
        assertThat(response.getBody().get(0).getCurrency()).isEqualTo(data.getCurrency());

        verify(dataBaseService, times(1)).getCryptoDataByTickerAndCurrency(ticker, currency);
    }
}
