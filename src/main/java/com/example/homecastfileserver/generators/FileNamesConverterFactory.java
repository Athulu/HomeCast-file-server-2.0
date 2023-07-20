package com.example.homecastfileserver.generators;

import com.example.homecastfileserver.converters.FileNamesConverter;
import com.example.homecastfileserver.converters.CustomSeriesConverter;
import com.example.homecastfileserver.converters.DefaultConverter;
import com.example.homecastfileserver.converters.MovieConverter;

public class FileNamesConverterFactory {
    public static FileNamesConverter getFileNameConverter(String name) {
        int count = name.split("\\.").length;
        if (count == 2) return new MovieConverter(name);
        else if (count == 3) return new CustomSeriesConverter(name);
        else return new DefaultConverter(name);
    }
}