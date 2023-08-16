package com.example.homecastfileserver.dto;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VideoDTO {
    private String episode;
    private String title;
    private String subtitle;
    private String thumb;
    private String image480x270;
    private String image780x1200;
    private Integer duration;
    private List<HashMap<String, String>> sources;

}
