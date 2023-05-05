package com.example.homecastfileserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FileService {
    @Autowired
    private ResourceLoader resourceLoader;


    public Mono<Resource> getVideo(String titlepath){
        return Mono.fromSupplier(()->resourceLoader.
                getResource(titlepath));
    }
}
