package com.example.homecastfileserver.mappers;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatGPTConfigObject {
    private String token;
    private Boolean istokenactive;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIstokenactive() {
        return istokenactive;
    }

    public void setIstokenactive(Boolean istokenactive) {
        this.istokenactive = istokenactive;
    }
}
