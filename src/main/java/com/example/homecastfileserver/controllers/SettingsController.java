package com.example.homecastfileserver.controllers;

import com.example.homecastfileserver.services.SettingsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
