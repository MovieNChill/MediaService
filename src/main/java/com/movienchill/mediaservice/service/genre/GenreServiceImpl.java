package com.movienchill.mediaservice.service.genre;

import com.movienchill.mediaservice.domain.dto.GenreDTO;
import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.repository.GenreDAO;
import com.movienchill.mediaservice.service.IGenericService;
import com.movienchill.mediaservice.service.media.MediaService;
import com.movienchill.mediaservice.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService, IGenericService<Genre, GenreDTO> {
    @Autowired
    private GenreDAO genreDAO;

    @Override
    public List<GenreDTO> findAll() {
        return null;
    }

    @Override
    public GenreDTO findById(Long id) {
        try {
            if (genreDAO.findById(id).isPresent()) {
                return Mapper.map(genreDAO.findById(id).get(), GenreDTO.class);
            } else {
                log.error("The genre is not present in DB");
            }
        } catch (Exception e) {
            log.error("An error occured while retrieving genre id=[{}] : {} ", id, e.getMessage());
        }

        return null;
    }

    @Override
    public List<GenreDTO> findAllGenreByMedia(Media media) {
        try {
            List<Genre> listGenre = genreDAO.findAllByMedia(media);

            if (listGenre != null) {
                // Mapping to DTO
                List<GenreDTO> listGenreDTO = new ArrayList<>();
                for (Genre genre : listGenre) {
                    listGenreDTO.add(new GenreDTO(
                            genre.getId(),
                            genre.getMedia().getId(),
                            genre.getGenre()
                    ));
                }
                return listGenreDTO;
            }
        } catch (Exception e) {
            log.error("An error occured while retrieving all genre of media id=[{}] : {}", media.getId(), e.getMessage());
        }

        return null;
    }

    @Override
    public boolean create(GenreDTO entityDto) {
        return false;
    }

    @Override
    public void save(Genre entity) {
        try {
            genreDAO.save(entity);
        } catch (Exception e) {
            log.error("Error while saving the entity : {}", e.getMessage());
        }
    }

    @Override
    public void delete(Genre entity) {
        try {
            genreDAO.delete(entity);
        } catch (Exception e) {
            log.error("Error while deleting the entity : {}", e.getMessage());
        }
    }
}
