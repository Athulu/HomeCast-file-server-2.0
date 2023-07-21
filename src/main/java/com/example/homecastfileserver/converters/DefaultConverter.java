package com.example.homecastfileserver.converters;

public class DefaultConverter extends FileNamesConverter {
    private DefaultConverter(String fileName, String[] splittedFileName, String name, String episode) {
        super(fileName, splittedFileName, name, episode);
    }

    public static DefaultConverter create(String name){
        return new DefaultConverter(name, name.split("\\."), name, "");
    }
}
