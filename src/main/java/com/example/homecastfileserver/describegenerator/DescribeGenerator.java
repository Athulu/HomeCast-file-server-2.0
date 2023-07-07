package com.example.homecastfileserver.describegenerator;

import com.example.homecastfileserver.converters.FileNamesConverter;

public interface DescribeGenerator {
    String getDescription(FileNamesConverter converter);
}
