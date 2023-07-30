package com.example.homecastfileserver.generators;

import com.example.homecastfileserver.configs.HomeCastConfig;
import com.example.homecastfileserver.converters.FileNamesConverter;
import com.example.homecastfileserver.dao.Source;
import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.generators.describegenerator.DescribeGenerator;
import com.example.homecastfileserver.services.VideosService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@AllArgsConstructor
public class VideoObjectGenerator {
    public static final String HASHCODE_FILE = "C:\\HomeCast\\hashcode.txt";
    private final DescribeGenerator describeGenerator;
    private final VideosService videosService;
    private final HomeCastConfig homeCastConfig;

    public void updateDirectoryContent(){
        List<String> videosFileNames = videosService.getVideosFileNames(); //nazwy plików z bazy danych
        List<String> videosDirFileNames = getAllDirFiles(); //nazwy plików w folderze

        //zapisywanie do bazy danych plików, które są w folderze, a nie ma w bazie danych
        List<String> toSaveList = new LinkedList<>();
        for (String fileName:videosDirFileNames)
            if(!videosFileNames.contains(fileName))
                toSaveList.add(fileName);

        for (Video video: createVideoObjectsFromFileNames(toSaveList))
            videosService.save(video);

        //usuwanie zawartości z bazy danych, której nie ma w folderze
        videosFileNames.removeAll(videosDirFileNames);
        for (String fileName: videosFileNames)
            videosService.remove(videosService.getVideoByFileName(fileName));
    }

    private List<Video> createVideoObjectsFromFileNames(List<String> videoFileNames){
        List<Video> videoList = new LinkedList<>();
        String episode, title, subtitle, thumb, image480x270, image780x1200;
        int duration;
        Source source;
        for (String fileName: videoFileNames) {
            //TODO: FileNamesConverter tutaj nie może być w tej formie
            FileNamesConverter fileNamesConverter = FileNamesConverterFactory.getFileNameConverter(fileName);
            episode = fileNamesConverter.getEpisode();
            title = fileNamesConverter.getTitle();
            subtitle = describeGenerator.getDescription(fileNamesConverter);
            thumb = fileName.replace(".mp4", "") + "480x270.png";
            image480x270 = thumb;
            image780x1200 = "bbb.png";
            duration = 100;
            source = new Source("videos/mp4", "mp4", fileName);
            videoList.add(new Video(fileName, episode, title, subtitle, thumb, image480x270, image780x1200, duration, source));
        }
        return videoList;
    }

    //title, episode

    private List<String> getAllDirFiles() {
        return getStrings(homeCastConfig.getMp4dir());
    }

    public void initializeCheckOfChanges() {
        int hashcode = getAllDirFiles().hashCode();
        if (isDirectoryDifferent(hashcode)) {
            try {
                PrintWriter zapis = new PrintWriter(homeCastConfig.getIp() + "/hashcode.txt"); //tutaj
                zapis.println(hashcode);
                zapis.close();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
            updateDirectoryContent();
        }
    }

    private boolean isDirectoryDifferent(int hashcode) {
        String text;

        try {
            BufferedReader brTest = new BufferedReader(new FileReader(HASHCODE_FILE));
            text = brTest.readLine();
        } catch (Exception e) {
            System.err.println("BufferedReader error");
            text = "1";
        }

        if (Integer.parseInt(text) != hashcode)
            return true;

        return false;
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
