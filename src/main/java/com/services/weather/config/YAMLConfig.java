package com.services.weather.config;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getDataUpdateDelay() {
        return dataUpdateDelay;
    }

    public void setDataUpdateDelay(int dataUpdateDelay) {
        this.dataUpdateDelay = dataUpdateDelay;
    }

    private int dataUpdateDelay;


    private String apiKey;
}
