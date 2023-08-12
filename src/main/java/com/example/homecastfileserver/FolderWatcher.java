package com.example.homecastfileserver;

import com.example.homecastfileserver.configs.HomeCastConfig;
import com.example.homecastfileserver.generators.ThumbnailGenerator;
import com.example.homecastfileserver.generators.VideoObjectGenerator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class FolderWatcher {
    private static final Logger logger = LoggerFactory.getLogger(FolderWatcher.class);
    private final ThumbnailGenerator thumbnailGenerator;
    private final VideoObjectGenerator videoObjectGenerator;
    private final HomeCastConfig homeCastConfig;

    @EventListener(ContextRefreshedEvent.class)
    public void run() throws Exception {
        createDirectoriesIfNotExists();
        homeCastConfig.setIpAdress(); //pobranie IP klasy C adresu prywatnego i ustawienie go
        thumbnailGenerator.generateThumbnails();
        videoObjectGenerator.initializeCheckOfChanges();

        // Tworzymy obiekt WatchService dla folderu, którego zmiany chcemy monitorować
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path folder = Paths.get(homeCastConfig.getMp4dir());
        WatchKey key = folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        Map<WatchKey, Path> keyMap = new HashMap<>();
        keyMap.put(key, folder);
        Set<Path> setOfPaths = new HashSet<>();

        while (true) {
            key = watchService.take();
            Path dir = keyMap.get(key);

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                Path path = Paths.get(homeCastConfig.getMp4dir() + event.context().toString());
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path createdFile = dir.resolve((Path) event.context());
                    logger.info("Nowy plik: " + createdFile);

                    // Sprawdzamy, czy rozmiar pliku się nie zmienia przez określony czas
                    long fileSize = Files.size(createdFile);
                    Thread.sleep(2000);
                    long newFileSize = Files.size(createdFile);

                    if (fileSize == newFileSize) {
                        logger.info("Plik w całości przeniesiony: " + createdFile);
                        setOfPaths.add(path);
                    } else {
                        logger.info("Plik nie został w całości przeniesiony: " + createdFile);
                    }
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    logger.info("Usunięto plik: " + event.context().toString());
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    logger.info("Zmodyfikowano plik: " + event.context().toString());
                }
            }

            if (setOfPaths.size() != 0) {
                for (Path path : setOfPaths) {
                    thumbnailGenerator.generateThumbnail(path);
                }
                setOfPaths.clear();
            }

            videoObjectGenerator.initializeCheckOfChanges();

            boolean valid = key.reset();
            if (!valid) {
                keyMap.remove(key);
                if (keyMap.isEmpty()) {
                    break;
                }
            }
        }
    }
    void createDirectoriesIfNotExists(){
        try {
            Path imagesPath = Paths.get(homeCastConfig.getImagesdir());
            Path mp4Path = Paths.get(homeCastConfig.getMp4dir());
            if(!Files.exists(imagesPath)){
                Files.createDirectories(imagesPath);
                logger.info("Stworzono folder: " + imagesPath);
            }
            if(!Files.exists(mp4Path)){
                Files.createDirectories(mp4Path);
                logger.info("Stworzono folder: " + mp4Path);
            }
        } catch (IOException e) {
            logger.error("Nie udało się stworzyć folderów");
        }
    }
}

