package com.example.homecastfileserver;

import com.example.homecastfileserver.generators.JsonFileGenerator;
import com.example.homecastfileserver.generators.ThumbnailGenerator;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.file.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class FolderWatcher {
    private final ThumbnailGenerator thumbnailGenerator;
    private final JsonFileGenerator jsonFileGenerator;

    public FolderWatcher(ThumbnailGenerator thumbnailGenerator, JsonFileGenerator jsonFileGenerator) {
        this.thumbnailGenerator = thumbnailGenerator;
        this.jsonFileGenerator = jsonFileGenerator;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void run() throws Exception {
        // Tworzymy obiekt WatchService dla folderu, którego zmiany chcemy monitorować
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path folder = Paths.get("C:\\HomeCast\\mp4\\");
        WatchKey key = folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

        Map<WatchKey, Path> keyMap = new HashMap<>();
        keyMap.put(key, folder);

        thumbnailGenerator.generateThumbnails();
        jsonFileGenerator.initializeCheckOfChanges();

        Set<Path> setOfPaths = new HashSet<>();

        while (true) {
            key = watchService.take();
            Path dir = keyMap.get(key);

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                Path path = Paths.get("C:\\HomeCast\\mp4\\" + event.context().toString());
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path createdFile = dir.resolve((Path) event.context());
                    System.out.println("Nowy plik: " + createdFile);

                    // Sprawdzamy, czy rozmiar pliku się nie zmienia przez określony czas
                    long fileSize = Files.size(createdFile);
                    Thread.sleep(2000);
                    long newFileSize = Files.size(createdFile);

                    if (fileSize == newFileSize) {
                        System.out.println("Plik w całości przeniesiony: " + createdFile);
                        setOfPaths.add(path);
                    } else {
                        System.out.println("Plik nie został w całości przeniesiony: " + createdFile);
                    }

                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("Usunięto plik: " + event.context().toString());
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("Zmodyfikowano plik: " + event.context().toString());
                }
            }

            if (setOfPaths.size() != 0) {
                for (Path path : setOfPaths) {
                    thumbnailGenerator.generateThumbnail(path);
                }
                jsonFileGenerator.createJsonFile();
                setOfPaths.clear();
            }

            boolean valid = key.reset();
            if (!valid) {
                keyMap.remove(key);
                if (keyMap.isEmpty()) {
                    break;
                }
            }
        }
    }
}

