package com.example.homecastfileserver.generators;

import com.example.homecastfileserver.converters.FileNamesConverter;
import com.example.homecastfileserver.converters.CustomSeriesConverter;
import com.example.homecastfileserver.converters.DefaultConverter;
import com.example.homecastfileserver.converters.MovieConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNamesConverterFactory {
    public static FileNamesConverter getFileNameConverter(String name) {
        int count = name.split("\\.").length;
        if (count == 2) return MovieConverter.create(name);
        else if (count == 3) return CustomSeriesConverter.create(name);
        else return DefaultConverter.create(name);
    }

    public static boolean isShindenPatternMatched(String input) {
        String pattern = ".+\\(anime\\) - Odcinek \\d+.+?Shinden";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }
}

