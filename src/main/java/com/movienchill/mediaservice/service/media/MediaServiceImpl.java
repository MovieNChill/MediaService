package com.movienchill.mediaservice.service.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.domain.dto.RecommendationDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.model.Star;
import com.movienchill.mediaservice.domain.model.Writer;
import com.movienchill.mediaservice.domain.repository.GenreDAO;
import com.movienchill.mediaservice.domain.repository.MediaDAO;
import com.movienchill.mediaservice.domain.repository.external.tmdb.TmdbDAO;
import com.movienchill.mediaservice.utils.GlobalProperties;
import com.movienchill.mediaservice.domain.repository.StarDAO;
import com.movienchill.mediaservice.domain.repository.WriterDAO;
import com.movienchill.mediaservice.utils.Mapper;
import com.movienchill.mediaservice.utils.WebService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaDAO mediaDAO;
    @Autowired
    private GenreDAO genreDAO;
    @Autowired
    private WriterDAO writerDAO;
    @Autowired
    private StarDAO starDAO;

    @Autowired
    private TmdbDAO tmdbDAO;

    @Autowired
    private GlobalProperties globalProperties;

    @Override
    public List<MediaDTO> findAll() {
        return null;
    }

    @Override
    public List<Genre> getGenres() {
        return genreDAO.findAll();
    }

    @Override
    public List<MediaDTO> findAllWithFilter(Specification<Media> spec, Pageable pageable) {
        try {
            List<Media> listMedia = mediaDAO.findAll(spec, pageable);

            if (listMedia != null) {
                return Mapper.mapList(listMedia, MediaDTO.class);
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
    public String getRecommendation(RecommendationDTO recommendationDTO) {
        // Call the Python Backend with the recommendationDTO
        String response = WebService.post(globalProperties.getUrlRecommend(), recommendationDTO);

        // Check the response and extraction of the name of media
        if (response != null) {
            // Extract media name
            JSONObject jsonObject = new JSONObject(response);
            return Arrays.stream(jsonObject.getJSONObject("recommended_movie").getJSONObject("title").toString().split(":")[1].split("}")).toList().get(0);

            // If no we get info from API and save it

            // Return it to the controller
        }
        return null;
    }

    @Override
    public boolean create(MediaDTO entityDto) {
        // get genres
        List<Genre> listeGenre = new ArrayList<>();
        for (String genre : entityDto.getGenres()) {
            listeGenre.add(genreDAO.findByName(genre));
        }
        entityDto.setGenres(null);
        // get or add writers
        List<Writer> listeWriter = new ArrayList<>();
        for (String writerString : entityDto.getWriters()) {
            Writer writer = writerDAO.findByName(writerString);
            if (writer != null) {
                listeWriter.add(writer);
            } else {
                writer = new Writer(writerString);
                writerDAO.save(writer);
                listeWriter.add(writer);
            }
        }
        entityDto.setWriters(null);
        // get or add stars
        List<Star> listeStar = new ArrayList<>();
        for (String starString : entityDto.getStars()) {
            Star star = starDAO.findByName(starString);
            if (star != null) {
                listeStar.add(star);
            } else {
                star = new Star(starString);
                starDAO.save(star);
                listeStar.add(star);
            }
        }
        entityDto.setStars(null);

        // Mapping DTO to entity
        Media media = Mapper.map(entityDto, Media.class);
        media.setGenres(listeGenre);
        media.setWriters(listeWriter);
        media.setStars(listeStar);
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
