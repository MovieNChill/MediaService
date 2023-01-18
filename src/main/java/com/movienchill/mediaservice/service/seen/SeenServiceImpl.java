package com.movienchill.mediaservice.service.seen;


import com.movienchill.mediaservice.domain.dto.SeenDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.model.Seen;
import com.movienchill.mediaservice.domain.repository.seenDAO;
import com.movienchill.mediaservice.service.IGenericService;

import com.movienchill.mediaservice.utils.Mapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


import java.util.List;


@Slf4j
@Service
public class SeenServiceImpl implements SeenService, IGenericService<Seen, SeenDTO> {
    private final com.movienchill.mediaservice.domain.repository.seenDAO seenDAO;

    public SeenServiceImpl(seenDAO seenDAO) {
        this.seenDAO = seenDAO;
    }

    private final String API_TMDB_URL = "https://api.themoviedb.org/3/";
    private final String API_TMDB_KEY = "729743283360120d3d45dc0dbb666a58";
    @Override
    public List<SeenDTO> findAll() {
        return null;
    }

    @Override
    public SeenDTO findById(Long id) {
        return null;
    }

    @Override
    public boolean create(SeenDTO entityDto) {
        // Mapping DTO to entity
        Seen seen = Mapper.map(entityDto, Seen.class);
        // Saving
        System.out.println(seen.toString());

        seenDAO.save(seen);
        return Boolean.TRUE;
    }

    @Override
    public void save(Seen entity) {

    }

    @Override
    public void delete(Long id) {

    }

    public Seen findMediaByUsr(Long usr_id){
        return seenDAO.findByUserId(usr_id);
    }


}
