package com.movienchill.mediaservice.service.media;

import com.movienchill.mediaservice.domain.dto.FilterDTO;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.repository.MediaDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaDAO mediaDAO;

    @Override
    public List<MediaDTO> findAll() {
        return null;
    }

    @Override
    public List<MediaDTO> findAllWithFilter(FilterDTO filter) {
        // Analyse des filtres
        if(filter.getNumberPage() != null && filter.getNumberElement() != null) {

        } else {
            log.error("Erreur");
        }

        // Recuperation des medias


        // Mappage en DTO

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
