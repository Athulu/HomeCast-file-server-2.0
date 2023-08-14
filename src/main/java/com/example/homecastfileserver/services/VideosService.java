package com.example.homecastfileserver.services;

import com.example.homecastfileserver.configs.HomeCastConfig;
import com.example.homecastfileserver.dao.Video;
import com.example.homecastfileserver.dto.UltimateDTO;
import com.example.homecastfileserver.dto.VideoDTO;
import com.example.homecastfileserver.exceptions.KremowkaException;
import com.example.homecastfileserver.mappers.VideosMapper;
import com.example.homecastfileserver.repositories.VideosRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideosService {
    private static final Logger logger = LoggerFactory.getLogger(VideosService.class);
    private final EntityManager entityManager;
    private final VideosRepository videosRepository;
    private final VideosMapper videosMapper;
    private final HomeCastConfig homeCastConfig;
    private static final int CYBER_POPE_2137 = 2137;

    public UltimateDTO getListOfVideosSortedByFileName(){
        return UltimateDTO.create(homeCastConfig.getIp() + "/mp4/",homeCastConfig.getIp() + "/images/", videosMapper.videoListToVideoDTOList(videosRepository.findAllBy().stream().sorted(Comparator.comparing((Video::getFileName))).collect(Collectors.toList())));
    }

    public UltimateDTO getListOfVideosSortedBySeriesAndEpisode(){
        List<VideoDTO> videoDTOList = videosMapper.videoListToVideoDTOList(videosRepository.findAllBy());
        List<VideoDTO> sortedList = new ArrayList<>();
        Map<String, List<VideoDTO>> listHashMap = new HashMap<>();

        for (VideoDTO videoDTO : videoDTOList) {
            if (!listHashMap.containsKey(videoDTO.getTitle())) {
                listHashMap.put(videoDTO.getTitle(), new ArrayList<>());
            }
            listHashMap.get(videoDTO.getTitle()).add(videoDTO);
        }

        for (String title: listHashMap.keySet()) {
            List<VideoDTO> lista = listHashMap.get(title).stream().sorted(Comparator.comparing((vid) -> {
                //format: Odcinek XX
                String[] fullEpisodeString = vid.getEpisode().split(" ");
                if (fullEpisodeString.length >= 2) {
                    try {
                        String episodeNumber = fullEpisodeString[1];
                        return Integer.parseInt(episodeNumber);
                    } catch (KremowkaException e) {
                        logger.error(e.getMessage());
                        return CYBER_POPE_2137;
                    }

                }
                return CYBER_POPE_2137;
            })).toList();
            sortedList.addAll(lista);
        }

        return UltimateDTO.create(homeCastConfig.getIp() + "/mp4/",homeCastConfig.getIp() + "/images/", sortedList);
    }

    public List<String> getVideosFileNames(){
        return videosRepository.findAllBy().stream().map(Video::getFileName).collect(Collectors.toList());
    }

    private static int extractEpisodeNumber(String episode) {
        String[] parts = episode.split(" ");
        if (parts.length >= 2) {
            try {
                return Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                return Integer.MAX_VALUE;
            }
        }
        return Integer.MAX_VALUE;
    }

    public Video getVideoByFileName(String fileName){
        return videosRepository.findByFileName(fileName);
    }

    @Transactional
    public void remove(Video video){
        Optional<Video> byFileName = Optional.ofNullable(videosRepository.findByFileName(video.getFileName()));
        if (byFileName.isPresent()) {
            Video vid = byFileName.get();
            entityManager.remove(vid);
        }
    }

    @Transactional
    public void save(Video video){
        entityManager.persist(video);
    }

    @Transactional
    public void updateVideoDescription(String fileName, String description) {
        Video video = videosRepository
                .findByFileName(fileName);
        video.setSubtitle(description);
        videosRepository.save(video);
    }
}
