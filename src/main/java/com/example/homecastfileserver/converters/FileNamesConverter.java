package com.example.homecastfileserver.converters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public abstract class FileNamesConverter {
    String fileName;
    String[] splittedFileName;
    String name;
    String episode;
}
