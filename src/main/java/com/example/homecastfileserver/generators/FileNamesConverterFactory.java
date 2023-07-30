package com.example.homecastfileserver.generators;

import com.example.homecastfileserver.converters.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNamesConverterFactory {
    public static FileNamesConverter getFileNameConverter(String fileName) {
        if(isShindenPatternMatched(fileName)) return ShindenConverter.create(fileName);
        else if (isCustomPatternMatched(fileName)) return CustomSeriesConverter.create(fileName);
        else if (isMoviePatternMatched(fileName)) return MovieConverter.create(fileName);
        else return DefaultConverter.create(fileName);
    }

    private static boolean isMoviePatternMatched(String fileName){
        String regex = ".*[^a-zA-Z0-9' ].*";
        return fileName.matches(regex);
    }

    private static boolean isCustomPatternMatched(String fileName){
        String regex = "^s\\d{2}e\\d{2}\\..*";
        return Pattern.matches(regex, fileName);
    }

    private static boolean isShindenPatternMatched(String fileName) {
        String pattern = ".+\\(anime\\) - Odcinek \\d+.+?Shinden.mp4";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(fileName);
        return matcher.matches();
    }
}

