package com.movienchill.mediaservice.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MediaDTO {
    private Long id;
    private String type;
    private String name;
    private Date releaseDate;
    private String director;
    private List<String> writers;
    private List<String> stars;
    private String description;
    private String imgUrl;
}
