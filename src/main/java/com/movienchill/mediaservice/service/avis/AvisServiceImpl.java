package com.movienchill.mediaservice.service.avis;

import com.movienchill.mediaservice.domain.dto.AvisDTO;
import com.movienchill.mediaservice.domain.model.Avis;
import com.movienchill.mediaservice.service.IGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AvisServiceImpl implements AvisService, IGenericService<Avis, AvisDTO> {

    @Override
    public List<AvisDTO> findAll() {
        return null;
    }

    @Override
    public AvisDTO findById(Long id) {
        return null;
    }

    @Override
    public AvisDTO save(Avis entity) {
        return null;
    }

    @Override
    public void delete(Avis entity) {

    }
}