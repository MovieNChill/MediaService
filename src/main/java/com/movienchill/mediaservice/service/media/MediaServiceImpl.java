package com.movienchill.mediaservice.service.media;

import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.repository.GenreDAO;
import com.movienchill.mediaservice.domain.repository.MediaDAO;
import com.movienchill.mediaservice.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaDAO mediaDAO;
    @Autowired
    private GenreDAO genreDAO;

    @Override
    public List<MediaDTO> findAll() {
        return null;
    }

    @Override
    public List<Genre> getGenres() {
        return genreDAO.findAll();
    }

    @Override
    public List<MediaDTO> findAllWithFilter(Specification<String> spec, Pageable pageable) {
        try {
            List<Media> listMedia = mediaDAO.findAll(spec, pageable);

            if (listMedia != null) {
                return Mapper.mapList(listMedia.stream().toList(), MediaDTO.class);
            }
        } catch (Exception e) {
            log.error("An error occured while retrieving medias : {}", e.getMessage());
        }

        return null;
    }

    @Override
    public MediaDTO findById(Long id) {
        /*
         * return mediaDAO.findById(id)
         * .map(media -> Mapper.map(media, MediaDTO.class))
         * .orElseThrow(() -> new
         * RuntimeException("AUcun medi trouvé pour l'identifiant " + id))
         */
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
        // Mapping DTO to entity
        List<Genre> listeGenre = new ArrayList<>();
        for (String genre : entityDto.getGenres()) {
            listeGenre.add(genreDAO.findByName(genre));
        }
        entityDto.setGenres(null);
        Media media = Mapper.map(entityDto, Media.class);
        media.setGenres(listeGenre);
        // Saving
        this.save(media);
        return true;
    }

    @Override
    public void save(Media entity) {
        try {
            mediaDAO.saveAndFlush(entity);
        } catch (Exception e) {
            log.error("Error while saving the entity : {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            mediaDAO.delete(mediaDAO.findById(id).get());
        } catch (Exception e) {
            log.error("Error while deleting the entity : {}", e.getMessage());
        }
    }
}
