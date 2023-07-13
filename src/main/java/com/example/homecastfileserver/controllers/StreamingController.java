package com.example.homecastfileserver;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
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

import static com.example.homecastfileserver.JsonFileGenerator.JSON_DB_FILE;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class StreamingController {
    private final StreamingService streamingService;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource fileResource = new FileSystemResource("C:\\HomeCast\\images\\" + filename);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @GetMapping("/mp4/{filename}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String filename) {
        filename = "C:\\HomeCast\\mp4\\" + filename;
        Resource video = streamingService.getVideoResource(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + video.getFilename() + "\"")
                .body(video);
    }

    @GetMapping("/db.json")
    public ResponseEntity<byte[]> showFile() throws IOException {
        Path path = Paths.get(JSON_DB_FILE);
        byte[] fileContent = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
