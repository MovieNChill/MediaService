package com.movienchill.mediaservice.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "genre")
    @ElementCollection
    private List<String> genre;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "director")
    private String director;

    @Column(name = "writers")
    @ElementCollection
    private List<String> writers;

    @Column(name = "stars")
    @ElementCollection
    private List<String> stars;

    @Column(name = "description")
    private String description;

    @Column(name = "imgUrl")
    private String imgUrl;
}
