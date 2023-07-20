package com.example.homecastfileserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UltimateDTO {
    private String mp4;
    private String images;
    private List<VideoDTO> videos;

    public static UltimateDTO create(String mp4, String images, List<VideoDTO> videos){
        return new UltimateDTO(mp4, images, videos);
    }
}
