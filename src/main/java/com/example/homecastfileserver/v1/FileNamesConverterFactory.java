package com.example.homecastfileserver.v1;

import com.example.homecastfileserver.v1.converters.FileNamesConverter;
import com.example.homecastfileserver.v1.converters.CustomSeriesConverter;
import com.example.homecastfileserver.v1.converters.DefaultConverter;
import com.example.homecastfileserver.v1.converters.MovieConverter;

public class FileNamesConverterFactory {
    public static FileNamesConverter getFileNameConverter(String name) {
        int count = name.split("\\.").length;
        if (count == 2) return new MovieConverter(name);
        else if (count == 3) return new CustomSeriesConverter(name);
        else return new DefaultConverter(name);
    }
}