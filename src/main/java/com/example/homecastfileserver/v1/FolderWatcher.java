import java.nio.file.*;

public class FolderWatcher {
    public static void main(String[] args) throws Exception {
        // Tworzymy obiekt WatchService dla folderu, którego zmiany chcemy monitorować
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path folder = Paths.get("C:\\HomeCast\\mp4");
        folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

        while (true) {
            // Oczekujemy na zdarzenia w WatchService
            WatchKey key = watchService.take();

            // Przetwarzamy zdarzenia dla zarejestrowanego folderu
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                // Wykonujemy odpowiednie akcje w zależności od rodzaju zdarzenia
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("Nowy plik: " + event.context().toString());
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("Usunięto plik: " + event.context().toString());
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("Zmodyfikowano plik: " + event.context().toString());
                }
            }

            // Resetujemy klucz i kontynuujemy nasłuchiwanie
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }
}

