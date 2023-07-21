package com.example.homecastfileserver.converters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public abstract class FileNamesConverter {
    String fileName;
    String[] splittedFileName;
    String name;
    String episode;
}
