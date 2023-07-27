package com.example.homecastfileserver.controllers;

import com.example.homecastfileserver.configs.HomeCastConfig;
import com.example.homecastfileserver.services.StreamingService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
@AllArgsConstructor
public class StreamingController {
    private final StreamingService streamingService;
    private final HomeCastConfig homeCastConfig;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource fileResource = new FileSystemResource(homeCastConfig.getImagesdir() + filename);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @GetMapping("/mp4/{filename}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String filename) {
        filename = homeCastConfig.getMp4dir() + filename;
        Resource video = streamingService.getVideoResource(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + video.getFilename() + "\"")
                .body(video);
    }
}
