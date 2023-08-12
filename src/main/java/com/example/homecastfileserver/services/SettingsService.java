package com.example.homecastfileserver.services;


import com.example.homecastfileserver.configs.ChatGPTConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SettingsService {
    private final ChatGPTConfig chatGPTConfig;
    private final InitializeService initializeService;

    public void setTokenChatGPT(String token, Boolean isActive){
        chatGPTConfig.setToken(token);
        chatGPTConfig.setTokenActive(isActive);
        initializeService.overrideJSONFile();
    }
}
