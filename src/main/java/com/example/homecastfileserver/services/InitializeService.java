package com.example.homecastfileserver.services;

import com.example.homecastfileserver.configs.ChatGPTConfig;
import com.example.homecastfileserver.configs.HomeCastConfig;
import com.example.homecastfileserver.generators.ThumbnailGenerator;
import com.example.homecastfileserver.generators.VideoObjectGenerator;
import com.example.homecastfileserver.mappers.ChatGPTConfigObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class InitializeService {
    private static final Logger logger = LoggerFactory.getLogger(InitializeService.class);
    private final ChatGPTConfig chatGPTConfig;
    private final HomeCastConfig homeCastConfig;
    private final ThumbnailGenerator thumbnailGenerator;
    private final VideoObjectGenerator videoObjectGenerator;

    public void initialize() throws Exception{
        createDirectoriesIfNotExists();
        createChatGPTJsonFileIfNotExists();
        loadDataFromJSONFile();
        homeCastConfig.setIpAdress(); //pobranie IP klasy C adresu prywatnego i ustawienie go
        thumbnailGenerator.generateThumbnails();
        videoObjectGenerator.initializeCheckOfChanges();
    }

    public void overrideJSONFile(){
        createChatGPTJsonFileIfNotExists();
        ChatGPTConfigObject newDataObject = new ChatGPTConfigObject(chatGPTConfig.getToken(), chatGPTConfig.getIsTokenActive());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(homeCastConfig.getHomecastdir() + "chatgpt.txt"), newDataObject);
            logger.info("Plik chatgpt.txt został zaktualizowany.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Nie udało się zaktualizować pliku chatgpt.txt");
        }
    }

    private void createDirectoriesIfNotExists(){
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

    private void createChatGPTJsonFileIfNotExists(){
        try {
            Path chatGPTFile = Paths.get(homeCastConfig.getHomecastdir() + "chatgpt.txt");
            if(!Files.exists(chatGPTFile)){
                ChatGPTConfigObject dataObject = new ChatGPTConfigObject("", false);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(new File(homeCastConfig.getHomecastdir() + "chatgpt.txt"), dataObject);
                logger.info("Plik chatgpt.txt został wygenerowany");
            }
        } catch (IOException e) {
            logger.error("Nie udało się wygenerować pliku chatgpt.txt");
            e.printStackTrace();
        }
    }

    private void loadDataFromJSONFile(){
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(homeCastConfig.getHomecastdir() + "chatgpt.txt");
            ChatGPTConfig fromJson = gson.fromJson(reader, ChatGPTConfig.class);
            chatGPTConfig.setToken(fromJson.getToken());
            chatGPTConfig.setIsTokenActive(fromJson.getIsTokenActive());
            logger.info("Dane z pliku chatgpt.txt załadowane");
        } catch (IOException e) {
            logger.error("Nie udało się załadować danych z pliku chatgpt.txt");
            e.printStackTrace();
        }
    }
}
