package com.movienchill.mediaservice.service.media;

import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.service.IGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService, IGenericService<Media, MediaDTO> {

    @Override
    public List<MediaDTO> findAll() {
        return null;
    }

    @Override
    public MediaDTO findById(Long id) {
        return null;
    }

    @Override
    public MediaDTO save(Media entity) {
        return null;
    }

    @Override
    public void delete(Media entity) {

    }
}
