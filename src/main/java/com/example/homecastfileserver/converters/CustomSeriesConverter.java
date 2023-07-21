package com.example.homecastfileserver.converters;

public class CustomSeriesConverter extends FileNamesConverter {
    private CustomSeriesConverter(String fileName, String[] splittedFileName, String name, String episode) {
        super(fileName, splittedFileName, name, episode);
    }

    public static CustomSeriesConverter create(String name){
        String[] splittedFileName = name.split("\\.");
        return new CustomSeriesConverter(name, splittedFileName, splittedFileName[1], splittedFileName[0]);
    }

}
