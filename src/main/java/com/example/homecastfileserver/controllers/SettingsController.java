package com.example.homecastfileserver.controllers;

import com.example.homecastfileserver.services.SettingsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;
    @PostMapping(value = "/token")
    @ResponseBody
    public String downloadFile(@RequestParam String token, @RequestParam(required = false) Boolean isActive) {
        if(isActive==null) isActive=false;
        settingsService.setTokenChatGPT(token, isActive);
        return "Ustawiono token!\nToken: " + token + "\nAktywny: " + isActive;
    }
}
