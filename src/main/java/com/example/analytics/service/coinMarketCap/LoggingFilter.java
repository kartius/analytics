package com.example.analytics.service.coinMarketCap;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(request, responseWrapper);
        } finally {
            logResponse(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper) {
        int statusCode = responseWrapper.getStatus();
        logger.log(Level.INFO, "HTTP Response Status: " + statusCode);

        byte[] responseBodyBytes = responseWrapper.getContentAsByteArray();
        String responseBody = new String(responseBodyBytes, StandardCharsets.UTF_8);
        logger.log(Level.INFO, "HTTP Response Body: " + responseBody);
    }
}