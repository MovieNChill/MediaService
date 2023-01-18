package com.movienchill.mediaservice.service.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.dto.RecommendationDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.repository.MediaDAO;
import com.movienchill.mediaservice.domain.repository.external.tmdb.TmdbDAO;
import com.movienchill.mediaservice.utils.GlobalProperties;
import com.movienchill.mediaservice.utils.Mapper;
import com.movienchill.mediaservice.utils.WebService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaDAO mediaDAO;

    @Autowired
    private TmdbDAO tmdbDAO;

    @Autowired
    private GlobalProperties globalProperties;

    @Override
    public List<MediaDTO> findAll() {
        return null;
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
    public MediaDTO getRecommendation(RecommendationDTO recommendationDTO) {
        // Call the Python Backend with the recommendationDTO
        String response = WebService.post(globalProperties.getUrlRecommend(), recommendationDTO);

        // Check the response and extraction of the name of media
        if (response != null) {
            // Extract media name

            // Check database if media present
            Media media = mediaDAO.findByName(response);
            if(media != null) {
                return Mapper.map(media, MediaDTO.class);
            } else {
                // Else -> call TMDb to get media info
                Integer idMediaTmbd = tmdbDAO.findIdByName(response);
                tmdbDAO.findByName(response);
            }

            // If no we get info from API and save it

            // Return it to the controller
        }

        return null;
    }

    @Override
    public boolean create(MediaDTO entityDto) {
        // Mapping DTO to entity
        Media media = Mapper.map(entityDto, Media.class);
        // Saving
        this.save(media);
        return true;
    }

    @Override
    public void save(Media entity) {
        try {
            mediaDAO.save(entity);
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
