package com.example.homecastfileserver.services;

import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.dto.VideoDTO;
import com.example.homecastfileserver.mappers.VideosMapper;
import com.example.homecastfileserver.repositories.VideosRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideosService {
    private final EntityManager entityManager;
    private final VideosRepository videosRepository;
    private final VideosMapper videosMapper;

    public List<VideoDTO> getListOfVideos(){
        return videosMapper.videoListToVideoDTOList(videosRepository.findAllBy());
    }

    public List<Integer> getVideosHashcodes(){
        return getListOfVideos().stream().map(VideoDTO::hashCode).collect(Collectors.toList());
    }

    public Video getVideoByHashcode(int hashcode){
        return videosRepository.findByHashcode(hashcode);
    }

    @Transactional
    public void remove(Video video){
        entityManager.remove(video);
    }

    @Transactional
    public void save(Video video){
        entityManager.persist(video);
    }
}
