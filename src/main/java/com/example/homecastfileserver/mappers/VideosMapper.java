package com.example.homecastfileserver.mappers;
import com.example.homecastfileserver.dao.Source;
import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.dto.VideoDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VideosMapper {
    @Mapping(target = "sources", source = "sources", qualifiedByName = "getSource")
    VideoDTO videoToVideoDTO(Video video);

    List<VideoDTO> videoListToVideoDTOList(List<Video> videoList);

    @Named("getSource")
    default List<HashMap<String, String>> getSource(Source sources) {
        if(sources == null) return null;
        HashMap<String, String> map = new HashMap<>();

        //TODO: To w takiej formie nie może pozostać
        map.put("mime", sources.getMime());
        map.put("type", sources.getType());
        map.put("url", sources.getUrl());
        List<HashMap<String, String>> sourcesList = new ArrayList<>();
        sourcesList.add(map);
        return sourcesList;
    }
}
