package com.movienchill.mediaservice.controller;

import com.movienchill.mediaservice.constants.Router;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.specification.builder.SpecificationBuilder;
import com.movienchill.mediaservice.service.media.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
    private final Integer DEFAULT_PAGE_VALUE = 0;
    private final Integer DEFAULT_SIZE_VALUE = 4;

    private final MediaService mediaService;

    @Autowired
    public MediaRestController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * Endpoint to get list of medias with filter in parameter if present
     * <p>
     * The parameter will be analysed with Regex to extract filters
     * The form need to be : "key:value,".
     * - "key" : The key in DB to filter
     * - ":" or "<" or ">" : For respectively "Equal", "Inferior", "Superior"
     * - "value" : The value wanted
     * <p>
     *
     * @param search A string with the multiple filter. Need to be under the form "key:value".
     * @param page   The current number of the page
     * @param size   The number of element in the page
     * @return a list of media
     */
    @GetMapping
    public ResponseEntity<List<MediaDTO>> getMediaWithFilter(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "search", required = false) String search) {

        // Filter analysis and Specification build
        SpecificationBuilder specificationBuilder = new SpecificationBuilder();
        Specification<String> spec = specificationBuilder.searchFilter(search);

        // Add default pageable if parameters "page" and "size" not presents
        if(page == null) {
            page = DEFAULT_PAGE_VALUE;
        }
        if(size == null) {
            size = DEFAULT_SIZE_VALUE;
        }

        // Get medias
        List<MediaDTO> listMedia = mediaService.findAllWithFilter(spec, PageRequest.of(page, size));

        if (listMedia != null) {
            return new ResponseEntity<>(listMedia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to create a media
     *
     * @param mediaDTO The MediaDTO
     * @return A response entity True if success else False
     */
    @PostMapping
    public ResponseEntity<Boolean> createMedia(@RequestBody @Validated MediaDTO mediaDTO) {
        boolean result = mediaService.create(mediaDTO);
        if (result) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get a media with his id in parameter
     *
     * @param id The id of the media
     * @return the mediaDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<MediaDTO> getMediaById(@PathVariable String id) {
        MediaDTO mediaDTO = mediaService.findById(Long.parseLong(id));
        if (mediaDTO != null) {
            return new ResponseEntity<>(mediaDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ONLY TO TEST
     * // TODO DELETE
     */
    @GetMapping("helloWorld")
    public String HelloWorld() {
        return "Hello World";
    }
}
