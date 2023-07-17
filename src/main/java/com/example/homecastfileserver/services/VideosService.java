package com.example.homecastfileserver.services;

import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.dto.VideoDTO;
import com.example.homecastfileserver.generators.describegenerator.DescribeGenerator;
import com.example.homecastfileserver.mappers.VideosMapper;
import com.example.homecastfileserver.repositories.VideosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VideosService {
    private final VideosRepository videosRepository;
    private final VideosMapper videosMapper;

    public static final String HOST_ADRESS = "http://192.168.1.109:8080";
    public static final String JSON_DB_FILE = "C:\\HomeCast\\db.json";
    public static final String MOVIES_DIRECTORY = "C:\\HomeCast\\mp4";
    public static final String HASHCODE_FILE = "C:\\HomeCast\\hashcode.txt";
    private static DescribeGenerator describeGenerator;

    public List<VideoDTO> getListOfVideos(){
        return videosMapper.videoListToVideoDTOList(videosRepository.findAllBy());
    }

    private static List<String> getAllDirFiles() {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream
                     = Files.newDirectoryStream(Paths.get(MOVIES_DIRECTORY))) {
            for (Path path : directoryStream) {
                fileNames.add(path.getFileName().toString());
            }
        } catch (IOException ex) {
        }
        return fileNames;
    }
}
