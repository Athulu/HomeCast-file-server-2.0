package com.example.homecastfileserver.controllers;

import com.example.homecastfileserver.configs.HomeCastConfig;
import com.example.homecastfileserver.services.SettingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class SettingsController {
    HomeCastConfig homeCastConfig;

    private final SettingsService settingsService;
    @PostMapping(value = "/token")
    @ResponseBody
    public String downloadFile(@RequestParam String token, @RequestParam(required = false) Boolean isActive) {
        if(isActive==null) isActive=false;
        settingsService.setTokenChatGPT(token, isActive);
        return "Ustawiono token!\nToken: " + token + "\nAktywny: " + isActive;
    }

    @GetMapping("/chatgpt")
    public ResponseEntity<byte[]> showFile() throws IOException {
        Path path = Paths.get(homeCastConfig.getHomecastdir()+ "chatgpt.txt");
        byte[] fileContent = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
