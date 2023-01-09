package com.movienchill.mediaservice.service.genre;

import com.movienchill.mediaservice.domain.dto.GenreDTO;
import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.service.IGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService, IGenericService<Genre, GenreDTO> {
    @Override
    public List<GenreDTO> findAll() {
        return null;
    }

    @Override
    public GenreDTO findById(Long id) {
        return null;
    }

    @Override
    public GenreDTO save(Genre entity) {
        return null;
    }

    @Override
    public void delete(Genre entity) {

    }
}
