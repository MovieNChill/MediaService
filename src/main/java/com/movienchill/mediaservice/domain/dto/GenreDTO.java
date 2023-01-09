package com.movienchill.mediaservice.domain.dto;

import com.movienchill.mediaservice.domain.model.Media;
import lombok.Data;

@Data
public class GenreDTO {
    private Long id;
    private Long idMedia;
    private String genre;

    public GenreDTO(Long id, Long idMedia, String genre) {
        this.id = id;
        this.idMedia = idMedia;
        this.genre = genre;
    }
}
