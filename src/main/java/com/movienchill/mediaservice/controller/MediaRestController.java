package com.movienchill.mediaservice.controller;

import com.movienchill.mediaservice.constants.Router;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.dto.PlatformDTO;
import com.movienchill.mediaservice.domain.dto.RecommendationDTO;
import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.specification.builder.SpecificationBuilder;
import com.movienchill.mediaservice.service.media.MediaService;
import com.movienchill.mediaservice.service.platform.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(Router.BASE_MEDIA)
@CrossOrigin(origins = "*")
public class MediaRestController {
    private final Integer DEFAULT_PAGE_VALUE = 0;
    private final Integer DEFAULT_SIZE_VALUE = 4;

    private final MediaService mediaService;

    private final PlatformService platformService;

    @Autowired
    public MediaRestController(MediaService mediaService, PlatformService platformService) {
        this.mediaService = mediaService;
        this.platformService = platformService;
    }

    @GetMapping("/genres")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> getGenres() {
        List<String> genres = mediaService.getGenres().stream().map(Genre::getName).collect(Collectors.toList());

        if (genres != null) {
            return new ResponseEntity<>(genres, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
     * @param search A string with the multiple filter. Need to be under the form
     *               "key:value".
     * @param page   The current number of the page
     * @return a list of media
     * @paramize The number of element in the page
     */
    @GetMapping
    public ResponseEntity<List<MediaDTO>> getMediaWithFilter(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "search", required = false) String search) {

        // Filter analysis and Specification build
        SpecificationBuilder specificationBuilder = new SpecificationBuilder();
        Specification<Media> spec = null;
        if (search != null) {
            spec = specificationBuilder.searchFilter(search);
        }

        // Add default pageable if parameters "page" and "size" not presents
        if (page == null) {
            page = DEFAULT_PAGE_VALUE;
        }
        if (size == null) {
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
        try {
            if (result) {
                return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to create medias
     *
     * @param mediaDTO The MediaDTO
     * @return A response entity True if success else False
     */
    @PostMapping("/list")
    public ResponseEntity<Boolean> createMedias(@RequestBody @Validated List<MediaDTO> mediasDTO) {
        Boolean result = true;
        try {
            for (MediaDTO mediaDTO : mediasDTO) {
                log.info("Creating Media " + mediasDTO.indexOf(mediaDTO) + "/" + mediasDTO.size());
                if (!mediaService.create(mediaDTO)) {
                    result = false;
                }
            }
            if (result) {
                return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
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

    @GetMapping("/{id}/platform")
    public ResponseEntity<List<PlatformDTO>> getMediaPlatform(@PathVariable String id) {
        MediaDTO mediaDTO = mediaService.findById(Long.parseLong(id));
        List<PlatformDTO> listPlatform = platformService.getPlatformInfo(mediaDTO.getName());
        return new ResponseEntity<>(listPlatform, HttpStatus.OK);
    }

    /**
     * Endpoint to get a media Recommendation.
     *
     * @param recommendationDTO The DTO
     * @return the recommended media
     */
    @PostMapping(Router.RECOMMEND)
    public ResponseEntity<String> getRecommendation(@RequestBody @Validated RecommendationDTO recommendationDTO) {
        return new ResponseEntity<>(mediaService.getRecommendation(recommendationDTO), HttpStatus.OK);
    }

    /**
     * ONLY TO TEST
     * // TODO DELETE
     */
    @DeleteMapping("/{id}")

    public Boolean deleteMediaById(@PathVariable String id) {
        mediaService.delete(Long.parseLong(id));
        return true;
    }

    @GetMapping("helloWorld")
    public String HelloWorld() {
        return "Hello World";
    }
}
