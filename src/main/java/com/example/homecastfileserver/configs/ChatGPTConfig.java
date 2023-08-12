package com.example.homecastfileserver.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "chatgpt")
public class ChatGPTConfig {

    private String token;
    private Boolean isTokenActive;
    public Boolean getTokenActive() {
        return isTokenActive;
    }

    public void setTokenActive(Boolean tokenActive) {
        isTokenActive = tokenActive;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}