package com.movienchill.mediaservice.service.genre;

import com.movienchill.mediaservice.domain.dto.GenreDTO;
import com.movienchill.mediaservice.domain.model.Media;

import java.util.List;

public interface GenreService {
    public List<GenreDTO> findAllGenreByMedia(Media Media);
}
