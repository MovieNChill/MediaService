package com.movienchill.mediaservice.service.media;

import com.movienchill.mediaservice.domain.dto.FilterDTO;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.repository.MediaDAO;
import com.movienchill.mediaservice.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        Page<Media> listMedia = null;

        // Analyse des filtres
        if (filter.getNumberPage() != null
                && filter.getNumberElement() != null
                && filter.getSort() != null) {

            // Recuperation des medias
            try {
                listMedia = mediaDAO.findAll(PageRequest.of(filter.getNumberPage(), filter.getNumberElement()));
            } catch (Exception e) {
                log.error("An error occured while retrieving medias");
            }

            if (listMedia != null && listMedia.getSize() > 0) {
                // Mapping DTO
                return Mapper.mapList(listMedia.stream().toList(), MediaDTO.class);
            }
        } else {
            log.error("An error occured during the analysis of filter");
            return null;
        }

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
