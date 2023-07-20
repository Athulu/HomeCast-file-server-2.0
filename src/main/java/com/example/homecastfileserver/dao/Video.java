package com.example.homecastfileserver.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoID;
    private String episode;
    private String title;
    private String subtitle;
    //TODO: usunąć thumb
    // poprawić image480x270 na image-480x270
    // image780x1200 na image-780x1200
    private String thumb;
    private String image480x270;
    private String image780x1200;
    private Integer duration;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sourceID", unique = true)
    private Source sources;
    private int hashcode;

    public Video(String episode, String title, String subtitle, String thumb, String image480x270, String image780x1200, Integer duration, Source sources, Integer hashcode) {
        this.episode = episode;
        this.title = title;
        this.subtitle = subtitle;
        this.thumb = thumb;
        this.image480x270 = image480x270;
        this.image780x1200 = image780x1200;
        this.duration = duration;
        this.sources = sources;
        this.hashcode = hashcode;
    }
}
