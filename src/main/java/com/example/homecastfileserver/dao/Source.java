package com.example.homecastfileserver.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "sources")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sourceID;
    private String mime;
    private String type;
    private String url;
    @OneToOne(mappedBy = "source", cascade = CascadeType.ALL)
    private Video video;

    public Source(String mime, String type, String url) {
        this.mime = mime;
        this.type = type;
        this.url = url;
    }
}
