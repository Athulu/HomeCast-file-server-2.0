package com.example.homecastfileserver.repositories;

import com.example.homecastfileserver.dao.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosRepository extends CrudRepository<Video, Long> {
    Video findByFileName(String fileName);
    List<Video> findAllBy();
}
