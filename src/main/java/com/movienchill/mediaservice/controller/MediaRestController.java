package com.movienchill.mediaservice.controller;

import com.movienchill.mediaservice.constants.Router;
import com.movienchill.mediaservice.domain.dto.FilterDTO;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.service.media.MediaService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(Router.BASE_MEDIA)
public class MediaRestController {
    private final MediaService mediaService;

    @Autowired
    public MediaRestController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * Endpoint to get list of medias with filter in parameter
     *
     * @param filter the filter
     * @return a list of media
     */
    @GetMapping
    @ApiOperation(value = "", nickname = "getMediaWithFilter")
    public ResponseEntity<List<MediaDTO>> getMediaWithFilter(@RequestBody @Validated FilterDTO filter) {
        List<MediaDTO> listMedia = mediaService.findAllWithFilter(filter);
        if (listMedia != null) {
            return new ResponseEntity<>(listMedia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get a media with his id
     *
     * @param id The id of the media
     * @return the mediaDTO
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "", nickname = "getMediaById")
    public ResponseEntity<MediaDTO> getMediaById(@PathVariable Long id) {
        MediaDTO mediaDTO = mediaService.findById(id);
        if (mediaDTO != null) {
            return new ResponseEntity<>(mediaDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public String HelloWorld() {
        return "Hello World";
    }
}
