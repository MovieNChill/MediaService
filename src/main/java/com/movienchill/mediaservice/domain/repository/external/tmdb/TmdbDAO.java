package com.movienchill.mediaservice.domain.repository.external.tmdb;

import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.dto.PlatformDTO;

import java.util.List;

public interface TmdbDAO {
    public Integer findIdByName(String name);

    public List<PlatformDTO> findPlatformInfoById(Integer id);

    public MediaDTO findByName(String name);
}
