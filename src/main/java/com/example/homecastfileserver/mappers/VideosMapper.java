package com.example.homecastfileserver.mappers;
import com.example.homecastfileserver.dao.Source;
import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.dto.VideoDTO;
import org.mapstruct.*;
import java.util.HashMap;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VideosMapper {
    @Mapping(target = "source", source = "source", qualifiedByName = "getSource")
    VideoDTO videoToVideoDTO(Video video);

    List<VideoDTO> videoListToVideoDTOList(List<Video> videoList);

    @Named("getSource")
    default HashMap<String, String> getSource(Source source) {
        if(source == null) return null;
        HashMap<String, String> map = new HashMap<>();

        //TODO: To w takiej formie nie może pozostać
        map.put("mime", source.getMime());
        map.put("type", source.getType());
        map.put("url", source.getUrl());

        return map;
    }
}
