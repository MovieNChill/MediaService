package com.movienchill.mediaservice.domain.dto;

import com.movienchill.mediaservice.domain.model.Media;
import lombok.Data;

import java.util.List;

@Data
public class SeenDTO {
    private Long userId;
    private MediaDTO media;
    private Long tmdb_id;

    public SeenDTO(Long userId, MediaDTO media, Long tmdb_id) {
        this.userId = userId;
        this.media = media;
        this.tmdb_id = tmdb_id;
    }
}
