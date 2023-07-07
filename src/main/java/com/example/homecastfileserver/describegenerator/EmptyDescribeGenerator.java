package com.example.homecastfileserver.describegenerator;

import com.example.homecastfileserver.converters.FileNamesConverter;

public class EmptyDescribeGenerator implements DescribeGenerator{
    @Override
    public String getDescription(FileNamesConverter converter) {
        return "";
    }
}
