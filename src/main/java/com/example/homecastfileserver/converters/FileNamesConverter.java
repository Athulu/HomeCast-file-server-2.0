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
    String title;
    String episode;
    public abstract String generateChatMessageForDescription();
    @Override
    public String toString() {
        return "title: " + this.title + "\nepisode: " + this.episode;
    }
}
