package com.example.homecastfileserver.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Data
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
    private Source source;

//    "sources": [{
//        "mime": "videos/mp4",
//        "type": "mp4",
//        "url": "Jujutsu Kaisen - Odcinek 1.mp4"
//    }],

}
