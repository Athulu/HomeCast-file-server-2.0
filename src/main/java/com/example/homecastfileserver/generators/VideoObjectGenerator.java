package com.example.homecastfileserver.generators;

import com.example.homecastfileserver.converters.FileNamesConverter;
import com.example.homecastfileserver.dao.Source;
import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.generators.describegenerator.DescribeGenerator;
import com.example.homecastfileserver.services.VideosService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoObjectGenerator {
    public static final String MOVIES_DIRECTORY = "C:\\HomeCast\\mp4";
    private final DescribeGenerator describeGenerator;
    private final VideosService videosService;

    public VideoObjectGenerator(DescribeGenerator describeGenerator, VideosService videosService) {
        this.describeGenerator = describeGenerator;
        this.videosService = videosService;
    }

    public void updateDirectoryContent(){
        List<Integer> videosHashcodes = videosService.getVideosHashcodes();
        List<Integer> directoryHashcodes = getAllDirFiles().stream().map(String::hashCode).toList();
        List<Integer> tempDirectoryHashcodes = new LinkedList<>(directoryHashcodes);
        tempDirectoryHashcodes.removeAll(videosHashcodes);
        List<String> videoFileNames = getAllDirFiles();

        for (String fileName: videoFileNames)
            if(videosHashcodes.contains(fileName.hashCode()))
                videoFileNames.remove(fileName);

        //TODO: przypisać do zmiennej i wrzucić do bazy danych
        for (Video video: createVideoObjectsFromFileNames(videoFileNames)) {
            videosService.save(video);
        }

        //usuwanie zawartości z bazy danych, której już nie ma w folderze
        videosHashcodes.retainAll(directoryHashcodes);
        for (int hashcode: videosHashcodes) {
            videosService.remove(videosService.getVideoByHashcode(hashcode));
        }

    }

    private List<Video> createVideoObjectsFromFileNames(List<String> videoFileNames){
        List<Video> videoList = new LinkedList<>();
        String episode, title, subtitle, thumb, image480x270, image780x1200;
        int duration;
        Source source;
        int hashcode;
        for (String fileName: videoFileNames) {
            //TODO: FileNamesConverter tutaj nie może być w tej formie
            FileNamesConverter fileNamesConverter = FileNamesConverterFactory.getFileNameConverter(fileName);
            episode = fileNamesConverter.getEpisode();
            title = fileNamesConverter.getName();
            subtitle = describeGenerator.getDescription(fileNamesConverter);
            thumb = fileName.replace(".mp4", "") + "480x270.png";
            image480x270 = thumb;
            image780x1200 = "bbb.png";
            duration = 100;
            source = new Source("videos/mp4", "mp4", fileName);
            hashcode = fileName.hashCode();
            videoList.add(new Video(episode, title, subtitle, thumb, image480x270, image780x1200, duration, source, hashcode));
        }
        return videoList;
    }

    private static List<String> getAllDirFiles() {
        return getStrings(MOVIES_DIRECTORY);
    }

    @NotNull
    static List<String> getStrings(String moviesDirectory) {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream
                     = Files.newDirectoryStream(Paths.get(moviesDirectory))) {
            for (Path path : directoryStream) {
                fileNames.add(path.getFileName().toString());
            }
        } catch (IOException ex) {
            System.out.println("Nie udało się wczytać nazw plików");
        }
        return fileNames;
    }
}
