package com.movienchill.mediaservice.domain.dto;

import com.movienchill.mediaservice.domain.model.Media;
import lombok.Data;

@Data
public class GenreDTO {
    private Long id;
    private Media media;
    private String genre;
}
