package com.example.homecastfileserver.converters;

public class ShindenConverter extends FileNamesConverter{
    private ShindenConverter(String fileName, String[] splittedFileName, String name, String episode) {
        super(fileName, splittedFileName, name, episode);
    }

    public static ShindenConverter create(String name){
        return new ShindenConverter(null, null, null, null);
    }
}
