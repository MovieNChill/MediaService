package com.movienchill.mediaservice.service.media;

import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.dto.FilterDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.service.IGenericService;

import java.util.List;

public interface MediaService extends IGenericService<Media, MediaDTO> {
    public List<MediaDTO> findAllWithFilter(FilterDTO filter);
}
