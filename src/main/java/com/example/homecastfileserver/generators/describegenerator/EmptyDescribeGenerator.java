package com.example.homecastfileserver.generators.describegenerator;

import com.example.homecastfileserver.converters.FileNamesConverter;
import org.springframework.stereotype.Component;

@Component
public class EmptyDescribeGenerator implements DescribeGenerator {
    @Override
    public String getDescription(FileNamesConverter converter) {
        return "";
    }
}
