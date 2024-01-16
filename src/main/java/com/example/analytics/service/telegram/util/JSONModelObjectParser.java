package com.example.analytics.service.telegram.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class JSONModelObjectParser {

    @Contract("_ -> new")
    public static @NotNull JSONObject getObject(@NotNull String urlRequest) throws IOException {
        final URL url = URI.create(urlRequest).toURL();

        String result;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader((InputStream) url.getContent(), StandardCharsets.UTF_8))) {
            result = in.lines().collect(Collectors.joining());
        }

        return new JSONObject(result);
    }
}
