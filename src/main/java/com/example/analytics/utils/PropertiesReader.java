package com.example.analytics.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private static Properties properties;

    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
