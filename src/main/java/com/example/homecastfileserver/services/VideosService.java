package com.example.homecastfileserver.services;

import com.example.homecastfileserver.configs.HomeCastConfig;
import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.dto.UltimateDTO;
import com.example.homecastfileserver.mappers.VideosMapper;
import com.example.homecastfileserver.repositories.VideosRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideosService {
    private final EntityManager entityManager;
    private final VideosRepository videosRepository;
    private final VideosMapper videosMapper;
    private final HomeCastConfig homeCastConfig;

    public UltimateDTO getListOfVideos(){
        return UltimateDTO.create(homeCastConfig.getIp() + "/mp4/",homeCastConfig.getIp() + "/images/", videosMapper.videoListToVideoDTOList(videosRepository.findAllBy()));
    }

    public List<Integer> getVideosHashcodes(){
        return videosRepository.findAllBy().stream().map(video -> video.getHashcode()).collect(Collectors.toList());
    }

    public Video getVideoByHashcode(int hashcode){
        return videosRepository.findByHashcode(hashcode);
    }

    @Transactional
    public void remove(Video video){
        Optional<Video> videosByHashcode = Optional.ofNullable(videosRepository.findByHashcode(video.getHashcode()));
        if (videosByHashcode.isPresent()) {
            Video vid = videosByHashcode.get();
            entityManager.remove(vid);
        }
    }

    @Transactional
    public void save(Video video){
        entityManager.persist(video);
    }
}
