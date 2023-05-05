package com.example.homecastfileserver;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class FileController {
    @Autowired
    private StreamingService streamingService;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource fileResource = new FileSystemResource("images/" + filename);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @GetMapping("/mp4/{filename}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String filename) {
        Resource video = streamingService.ge;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + video.getFilename() + "\"")
                .body(video);
    }

    @GetMapping("/db.json")
    public ResponseEntity<byte[]> showFile() throws IOException {
        Path path = Paths.get("db.json");
        byte[] fileContent = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN); // określenie typu MIME pliku
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
