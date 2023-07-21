package com.example.homecastfileserver.converters;

public class MovieConverter extends FileNamesConverter {
    public MovieConverter(String fileName, String[] splittedFileName, String name, String episode) {
        super(fileName, splittedFileName, name, episode);
    }

    public static MovieConverter create(String name){
        String[] splittedFileName = name.split("\\.");
        return new MovieConverter(name, splittedFileName, splittedFileName[0], "");
    }
}
