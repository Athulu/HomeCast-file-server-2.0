package com.example.homecastfileserver.controllers;


import com.example.homecastfileserver.dto.UltimateDTO;
import com.example.homecastfileserver.services.VideosService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
@AllArgsConstructor
public class VideosController {
    private final VideosService videosService;

    @GetMapping("/videos")
    public ResponseEntity<UltimateDTO> getVideos() {
        return ResponseEntity.ok().body(videosService.getListOfVideos());
    }
}
