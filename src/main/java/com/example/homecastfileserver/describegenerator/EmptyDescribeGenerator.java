package com.example.homecastfileserver.v1;

public class EmptyDescribeGenerator implements DescribeGenerator{
    @Override
    public String getDescription(int season, int episode, String series) {
        return "";
    }
}
