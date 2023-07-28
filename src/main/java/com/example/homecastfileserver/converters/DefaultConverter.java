package com.example.homecastfileserver.converters;

public class DefaultConverter extends FileNamesConverter {
    private DefaultConverter(String title, String episode) {
        super(title, episode);
    }

    public static DefaultConverter create(String fileName){
        return new DefaultConverter(fileName, "");
    }
}
