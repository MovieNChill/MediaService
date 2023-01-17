package com.movienchill.mediaservice.service.platform;

import com.movienchill.mediaservice.domain.dto.PlatformDTO;

import java.util.List;

public interface PlatformService {
    public List<PlatformDTO> getPlatformInfo(String movieName);
}
