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
        List<String> videoFileNames = getAllDirFiles(); //nazwy plikow
        List<Integer> videosHashcodes = videosService.getVideosHashcodes(); //hashcody z bazy danych
        List<Integer> directoryHashcodes = videoFileNames.stream().map(String::hashCode).toList(); //hashcody z folderu

//        for (String fileName: videoFileNames){
//            if(videosHashcodes.contains(fileName.hashCode())){
//                videoFileNames.remove(fileName);
//            }
//        }
        List<String> toSaveList = new LinkedList<>();

        for(int i = 0; i < videoFileNames.size(); i++){
            if(!videosHashcodes.contains(videoFileNames.get(i).hashCode())){
                toSaveList.add(videoFileNames.get(i));
            }
        }

        for (Video video: createVideoObjectsFromFileNames(toSaveList))
            videosService.save(video);

        //usuwanie zawartości z bazy danych, której już nie ma w folderze
        videosHashcodes.removeAll(directoryHashcodes);
        for (int hashcode: videosHashcodes)
            videosService.remove(videosService.getVideoByHashcode(hashcode));
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

    private List<String> getAllDirFiles() {
        return getStrings(homeCastConfig.getMp4dir());
    }

    public void initializeCheckOfChanges() {
        int hashcode = getAllDirFiles().hashCode();
        if (isDirectoryDifferent(hashcode)) {
            try {
                PrintWriter zapis = new PrintWriter("hashcode.txt");
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
