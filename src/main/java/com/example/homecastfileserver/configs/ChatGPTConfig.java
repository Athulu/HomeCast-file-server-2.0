package com.example.homecastfileserver.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "chatgpt")
public class ChatGPTConfig {
    private String token;
    private Boolean istokenactive = false;
    public Boolean getTokenActive() {
        return istokenactive;
    }

    public void setTokenActive(Boolean tokenActive) {
        istokenactive = tokenActive;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}