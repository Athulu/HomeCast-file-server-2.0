package com.example.homecastfileserver.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "sources")
//@Data
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
}
