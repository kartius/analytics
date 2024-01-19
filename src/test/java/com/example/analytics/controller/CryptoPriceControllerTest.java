package com.example.analytics.controller;

import com.example.analytics.service.coinMarketCap.CryptoPriceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptoPriceControllerTest {

    @MockBean
    private CryptoPriceService cryptoPriceService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("FInding data using CryptoPriceController method 'getCryptoPrice'")
    public void getCryptoPriceTest() {
        String ticker = "BTC";
        String currency = "USD";
        String expectedResponse = "Current price of BTC: 4435.00 USD";

        when(cryptoPriceService.getCryptoPrice(ticker, currency)).thenReturn(expectedResponse);

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/crypto/price/{ticker}/{convert}",
                String.class,
                ticker, currency
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(expectedResponse);

        verify(cryptoPriceService, times(1)).getCryptoPrice(ticker, currency);
    }
}

