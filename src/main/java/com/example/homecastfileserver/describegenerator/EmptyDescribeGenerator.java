package com.example.homecastfileserver.describegenerator;

public class EmptyDescribeGenerator implements DescribeGenerator{
    @Override
    public String getDescription(int season, int episode, String series) {
        return "";
    }
}
