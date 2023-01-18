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
    @Column(nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "media_genre", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "writers")
    @ElementCollection
    private List<String> writers;

    @Column(name = "stars")
    @ElementCollection
    private List<String> stars;

    @Column(name = "description", length = 2048)
    private String description;

    @Column(name = "imgUrl", length = 2048)
    private String imgUrl;
}