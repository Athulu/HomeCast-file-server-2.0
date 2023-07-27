package com.example.homecastfileserver.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "homecast")
public class HomeCastConfig {
    private String ip;
    private String imagesdir;
    private String mp4dir;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getImagesdir() {
        return imagesdir;
    }

    public void setImagesdir(String imagesdir) {
        this.imagesdir = imagesdir;
    }

    public String getMp4dir() {
        return mp4dir;
    }

    public void setMp4dir(String mp4dir) {
        this.mp4dir = mp4dir;
    }
}
