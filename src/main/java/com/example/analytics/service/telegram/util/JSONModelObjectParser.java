package com.example.analytics.service.telegram.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Slf4j
public class JSONModelObjectParser {

    @Contract("_ -> new")
    public static @NotNull JSONObject getObject(@NotNull String urlRequest) throws IOException {
        final URL url = new URL(urlRequest);
        final BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) url.getContent()));
        final StringBuilder result = new StringBuilder();

        String serverResponse;
        while ((serverResponse = in.readLine()) != null) {
            result.append(serverResponse);
        }

        return new JSONObject(result.toString());
    }
}
