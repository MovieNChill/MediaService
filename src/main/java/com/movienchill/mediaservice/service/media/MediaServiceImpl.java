package com.movienchill.mediaservice.service.media;

import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.repository.MediaDAO;
import com.movienchill.mediaservice.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public List<MediaDTO> findAllWithFilter(Specification<Media> spec, Pageable pageable) {
        Page<Media> listMedia = null;
        try {
            listMedia = mediaDAO.findAll(spec, pageable);

            if(listMedia != null) {
                return Mapper.mapList(listMedia.stream().toList(), MediaDTO.class);
            }
        } catch (Exception e) {
            log.error("An error occured while retrieving medias : {}", e.getMessage());
        }

        return null;
    }

    @Override
    public MediaDTO findById(Long id) {
        try {
            if (mediaDAO.findById(id).isPresent()) {
                return Mapper.map(mediaDAO.findById(id).get(), MediaDTO.class);
            } else {
                log.error("The media is not present in DB");
            }
        } catch (Exception e) {
            log.error("An error occured while retrieving media id=[{}] : {} ", id, e.getMessage());
        }

        return null;
    }

    @Override
    public boolean create(MediaDTO entityDto) {
        try {
            // Mapping DTO to entity
            Media media = Mapper.map(entityDto, Media.class);

            // Saving
            save(media);

            return true;
        } catch (Exception e) {
            log.error("An error occured while creating the media : {}", e.getMessage());
        }

        return false;
    }

    @Override
    public void save(Media entity) {
        try {
            mediaDAO.save(entity);
        } catch (Exception e) {
            log.error("Error while saving the entity : {}", e.getMessage());
        }
    }

    @Override
    public void delete(Media entity) {
        try {
            mediaDAO.delete(entity);
        } catch (Exception e) {
            log.error("Error while deleting the entity : {}", e.getMessage());
        }
    }
}
