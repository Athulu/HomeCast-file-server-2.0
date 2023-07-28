package com.example.homecastfileserver.generators;

import com.example.homecastfileserver.converters.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNamesConverterFactory {
    public static FileNamesConverter getFileNameConverter(String fileName) {
        int count = fileName.split("\\.").length;
        if(isShindenPatternMatched(fileName)) return ShindenConverter.create(fileName);

        return switch (count) {
            case 2 -> MovieConverter.create(fileName);
            case 3 -> CustomSeriesConverter.create(fileName);
            default -> DefaultConverter.create(fileName);
        };
    }

    public static boolean isShindenPatternMatched(String input) {
        String pattern = ".+\\(anime\\) - Odcinek \\d+.+?Shinden.mp4";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }
}

