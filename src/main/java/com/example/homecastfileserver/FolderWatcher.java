package com.example.homecastfileserver;

import com.example.homecastfileserver.describegenerator.ChatGPTDescribeGenerator;
import com.example.homecastfileserver.describegenerator.DescribeGenerator;
import com.example.homecastfileserver.describegenerator.EmptyDescribeGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class FolderWatcher {

    @PostConstruct
    public static void run() throws Exception {
        // Tworzymy obiekt WatchService dla folderu, którego zmiany chcemy monitorować
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path folder = Paths.get("C:\\HomeCast\\mp4\\");
        WatchKey key = folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

        Map<WatchKey, Path> keyMap = new HashMap<>(); // Mapa przechowująca klucze i odpowiadające im ścieżki
        keyMap.put(key, folder); // Dodanie klucza i ścieżki do mapy

        ThumbnailGenerator thumbnailGenerator = new ThumbnailGenerator();
        DescribeGenerator describeGenerator = new EmptyDescribeGenerator();
        JsonFileGenerator jsonFileGenerator = new JsonFileGenerator(describeGenerator);

        while (true) {
            // Oczekujemy na zdarzenia w WatchService
            key = watchService.take();
            Path dir = keyMap.get(key);

            // Przetwarzamy zdarzenia dla zarejestrowanego folderu
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                // Wykonujemy odpowiednie akcje w zależności od rodzaju zdarzenia
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path createdFile = dir.resolve((Path) event.context());
                    System.out.println("Nowy plik: " + createdFile.toString());

                    // Sprawdzamy, czy rozmiar pliku się nie zmienia przez określony czas
                    long fileSize = Files.size(createdFile);
                    Thread.sleep(2000); // Oczekujemy przez 1 sekundę
                    long newFileSize = Files.size(createdFile);

                    if (fileSize == newFileSize) {
                        System.out.println("Plik w całości przeniesiony: " + createdFile.toString());
                        Path path = Paths.get("C:\\HomeCast\\mp4\\" + event.context().toString());
                        thumbnailGenerator.generateThumbnail(path);
                        jsonFileGenerator.createJsonFile();
                    } else {
                        System.out.println("Plik nie został w całości przeniesiony: " + createdFile.toString());
                    }

                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("Usunięto plik: " + event.context().toString());
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("Zmodyfikowano plik: " + event.context().toString());
                }
            }

            // Resetujemy klucz i kontynuujemy nasłuchiwanie
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

