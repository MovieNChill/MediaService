package com.movienchill.mediaservice.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MediaDTO {
    private Long id;
    private String type;
    private String name;
    private List<String> genre;
    private Date releaseDate;
    private String director;
    private List<String> writers;
    private List<String> stars;
    private String description;
    private String imgUrl;

    public MediaDTO() {

    }
    public MediaDTO(Long id, String type, String name, List<String> genre, Date releaseDate, String director, List<String> writers, List<String> stars, String description, String imgUrl) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.director = director;
        this.writers = writers;
        this.stars = stars;
        this.description = description;
        this.imgUrl = imgUrl;
    }
}
