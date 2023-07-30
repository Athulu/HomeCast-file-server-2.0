package com.example.homecastfileserver.converters;

public class CustomSeriesConverter extends FileNamesConverter {
    private CustomSeriesConverter(String title, String episode) {
        super(title, episode);
    }

    public static CustomSeriesConverter create(String name){
        String[] splittedFileName = name.split("\\.");
        String title = splittedFileName[1];
        String episode = splittedFileName[0];
        return new CustomSeriesConverter(title, episode);
    }

}
