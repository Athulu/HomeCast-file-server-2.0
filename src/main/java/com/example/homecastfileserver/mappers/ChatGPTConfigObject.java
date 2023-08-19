package com.example.homecastfileserver.mappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChatGPTConfigObject {
    private String token;
    private Boolean istokenactive;
}
