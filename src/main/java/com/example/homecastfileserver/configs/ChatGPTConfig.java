package com.example.homecastfileserver.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
@Setter
public class ChatGPTConfig {
    private String token;
    private Boolean isTokenActive = false;
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setTokenActive(Boolean tokenActive) {
//        isTokenActive = tokenActive;
//    }
//
//    public Boolean getTokenActive() {
//        return isTokenActive;
//    }
}