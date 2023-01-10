package com.movienchill.mediaservice.controller;

import com.movienchill.mediaservice.constants.Router;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.specification.builder.SpecificationBuilder;
import com.movienchill.mediaservice.service.media.MediaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(Router.BASE_MEDIA)
public class MediaRestController {
    private final String PATTERN_SEARCH_REGEX = "(\\w+?)(:|<|>)(\\w+?),";

    private final MediaService mediaService;

    @Autowired
    public MediaRestController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * Endpoint to get list of medias with filter in parameter
     * The parameter will be analysed with Regex to extract differents filter
     * The form need to be : "key:value,".
     * - "key" : The key in DB to filter
     * - ":" or "<" or ">" or "%" : For respectively "Equal", "Inferior", "Superior", "Like"
     * - "value" : The value wanted
     *
     * @param search A string with the multiple filter. Need to be under the form "key:value"
     * @return a list of media
     */
    @GetMapping
    @ApiOperation(value = "", nickname = "getMediaWithFilter")
    public ResponseEntity<List<MediaDTO>> getMediaWithFilter(@RequestParam(value = "search") String search) {
        SpecificationBuilder builder = new SpecificationBuilder();

        // Search of filters
        Pattern pattern = Pattern.compile(PATTERN_SEARCH_REGEX);
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Media> spec = builder.build();

        // Get medias
        List<MediaDTO> listMedia = mediaService.findAllWithFilter(spec);

        if (listMedia != null) {
            return new ResponseEntity<>(listMedia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to create a media
     * @param mediaDTO The MediaDTO
     * @return A response entity True if success else False
     */
    @PostMapping
    @ApiOperation(value = "", nickname = "createMedia")
    public ResponseEntity<Boolean> createMedia(@RequestBody @Validated MediaDTO mediaDTO) {
        boolean result = mediaService.create(mediaDTO);
        if (result) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("helloWorld")
    public String HelloWorld() {
        return "Hello World";
    }
}
