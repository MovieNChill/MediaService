package com.movienchill.mediaservice.service.media;

import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.dto.RecommendationDTO;
import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.service.IGenericService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface MediaService extends IGenericService<Media, MediaDTO> {
    public List<MediaDTO> findAllWithFilter(Specification<Media> spec, Pageable pageable);

    public List<Genre> getGenres();

    public String getRecommendation(RecommendationDTO recommendationDTO);
}
