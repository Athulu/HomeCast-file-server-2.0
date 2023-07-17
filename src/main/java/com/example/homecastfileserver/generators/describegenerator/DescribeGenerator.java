package com.example.homecastfileserver.generators.describegenerator;

import com.example.homecastfileserver.converters.FileNamesConverter;
import org.springframework.stereotype.Component;

public interface DescribeGenerator {
    String getDescription(FileNamesConverter converter);
}
