package com.example.homecastfileserver.converters;

public class MovieConverter extends FileNamesConverter {
    public MovieConverter(String title, String episode) {
        super(title, episode);
    }

    public static MovieConverter create(String fileName){
        fileName = fileName.replace(".mp4", "");
        String[] splittedFileName = fileName.split("\\.");
        String title = splittedFileName[0];
        return new MovieConverter(title, "");
    }

    @Override
    public String generateChatMessageForDescription() {
        return "Napisz mi krótki opis filmu: " + title + " nie zdradzający jego fabuły";
    }
}
