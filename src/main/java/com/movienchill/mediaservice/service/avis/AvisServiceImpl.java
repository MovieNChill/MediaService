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
    public boolean create(AvisDTO entityDto) {
        return false;
    }

    @Override
    public void save(Avis entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
