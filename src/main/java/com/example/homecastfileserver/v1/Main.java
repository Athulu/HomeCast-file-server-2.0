package com.example.homecastfileserver.v1;

import com.example.homecastfileserver.describegenerator.DescribeGenerator;
import com.example.homecastfileserver.describegenerator.EmptyDescribeGenerator;

public class Main {
    public static void main(String[] args) {
//        DescribeGenerator describeGenerator = new ChatGPTDescribeGenerator();
//        DescribeGenerator describeGenerator = new EmptyDescribeGenerator();
//        JsonFileGenerator jsonFileGenerator = new JsonFileGenerator(describeGenerator);
//        jsonFileGenerator.createJsonFile();
        ThumbnailGenerator thumbnailGenerator = new ThumbnailGenerator();
        thumbnailGenerator.generateThumbnails();
    }
}