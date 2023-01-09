package com.movienchill.mediaservice.service.platform;

import com.movienchill.mediaservice.domain.dto.PlatformDTO;
import com.movienchill.mediaservice.domain.model.Platform;
import com.movienchill.mediaservice.service.IGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PlatformServiceImpl implements PlatformService, IGenericService<Platform, PlatformDTO> {

    @Override
    public List<PlatformDTO> findAll() {
        return null;
    }

    @Override
    public PlatformDTO findById(Long id) {
        return null;
    }

    @Override
    public void save(Platform entity) {

    }

    @Override
    public void delete(Platform entity) {

    }
}
