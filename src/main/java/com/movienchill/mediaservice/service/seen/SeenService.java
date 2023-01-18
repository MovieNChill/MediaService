package com.movienchill.mediaservice.service.seen;

import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.dto.PlatformDTO;
import com.movienchill.mediaservice.domain.dto.SeenDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.model.Seen;
import com.movienchill.mediaservice.service.IGenericService;

import java.util.List;

public interface SeenService extends IGenericService<Seen, SeenDTO> {
    public Seen findMediaByUsr(Long usr_id);

}
