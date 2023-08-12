package com.example.homecastfileserver.generators;

import com.example.homecastfileserver.configs.ChatGPTConfig;
import com.example.homecastfileserver.configs.HomeCastConfig;
import com.example.homecastfileserver.converters.FileNamesConverter;
import com.example.homecastfileserver.dao.Source;
import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.generators.describegenerator.ChatGPTDescribeGenerator;
import com.example.homecastfileserver.generators.describegenerator.DescribeGenerator;
import com.example.homecastfileserver.generators.describegenerator.EmptyDescribeGenerator;
import com.example.homecastfileserver.services.VideosService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(VideoObjectGenerator.class);
    public static final String HASHCODE_FILE_NAME = "hashcode.txt";
    private final VideosService videosService;
    private final HomeCastConfig homeCastConfig;
    private final ChatGPTConfig chatGPTConfig;

    public void updateDirectoryContent() {
        List<String> videosFileNames = videosService.getVideosFileNames(); //nazwy plików z bazy danych
        List<String> videosDirFileNames = getAllDirFiles(); //nazwy plików w folderze

        //zapisywanie do bazy danych plików, które są w folderze, a nie ma w bazie danych
        List<String> toSaveList = new LinkedList<>();
        for (String fileName : videosDirFileNames)
            if (!videosFileNames.contains(fileName))
                toSaveList.add(fileName);

        List<Video> videoList = createVideoObjectsFromFileNames(toSaveList);
        for (Video video : videoList)
            videosService.save(video);

        //usuwanie zawartości z bazy danych, której nie ma w folderze
        videosFileNames.removeAll(videosDirFileNames);
        for (String fileName : videosFileNames)
            videosService.remove(videosService.getVideoByFileName(fileName));

        //uzupelnienie opisow
        for (Video video : videoList){
            FileNamesConverter fileNamesConverter = FileNamesConverterFactory.getFileNameConverter(video.getFileName());
            videosService.updateVideoDescription(video.getFileName(), getDescribeGenerator().getDescription(fileNamesConverter));
        }
    }

    private List<Video> createVideoObjectsFromFileNames(List<String> videoFileNames) {
        List<Video> videoList = new LinkedList<>();
        String episode, title, subtitle, thumb, image480x270, image780x1200;
        int duration;
        Source source;
        for (String fileName : videoFileNames) {
            FileNamesConverter fileNamesConverter = FileNamesConverterFactory.getFileNameConverter(fileName);
            episode = fileNamesConverter.getEpisode();
            title = fileNamesConverter.getTitle();
            subtitle = "";
            thumb = fileName.replace(".mp4", "") + "480x270.png";
            image480x270 = thumb;
            image780x1200 = "bbb.png";
            duration = 100;
            source = new Source("videos/mp4", "mp4", fileName);
            videoList.add(new Video(fileName, episode, title, subtitle, thumb, image480x270, image780x1200, duration, source));
        }
        return videoList;
    }

    private DescribeGenerator getDescribeGenerator(){
        if(chatGPTConfig.getTokenActive()) return new ChatGPTDescribeGenerator(chatGPTConfig);
        else return new EmptyDescribeGenerator();
    }

    private List<String> getAllDirFiles() {
        return getStrings(homeCastConfig.getMp4dir());
    }

    public void initializeCheckOfChanges() {
        int hashcode = getAllDirFiles().hashCode();
        if (isDirectoryDifferent(hashcode)) {
            try {
                PrintWriter zapis = new PrintWriter(homeCastConfig.getHomecastdir() + HASHCODE_FILE_NAME);
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
            BufferedReader brTest = new BufferedReader(new FileReader(homeCastConfig.getHomecastdir() + HASHCODE_FILE_NAME));
            text = brTest.readLine();
        } catch (Exception e) {
            logger.error("Nie można odnaleźć określonego pliku, więc stworzymy nowy");
            new File(homeCastConfig.getHomecastdir() + HASHCODE_FILE_NAME);
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
            logger.error("Nie udało się wczytać nazw plików");
        }
        return fileNames;
    }
}
