package com.example.homecastfileserver.controllers;

import lombok.AllArgsConstructor;
import netscape.javascript.JSObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TokenChatGPTController {

    @PostMapping(value = "/token")
    @ResponseBody
    public String downloadFile(@RequestParam String token, @RequestParam Boolean isActive) {
        return "Ustawiono token!\nToken: " + token + "\nAktywny: " + isActive;
    }
}
