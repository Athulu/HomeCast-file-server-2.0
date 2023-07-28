package com.example.homecastfileserver.converters;

public class MovieConverter extends FileNamesConverter {
    public MovieConverter(String title, String episode) {
        super(title, episode);
    }

    public static MovieConverter create(String fileName){
        String[] splittedFileName = fileName.split("\\.");
        String title = splittedFileName[0];
        return new MovieConverter(title, "");
    }
}
