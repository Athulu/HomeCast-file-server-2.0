package com.example.homecastfileserver.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    private String episode;
    private String title;
    private String subtitle;
    private String thumb;
    private String image480x270;
    private String image780x1200;
    private Integer duration;
    private HashMap<String, String> source;
}
