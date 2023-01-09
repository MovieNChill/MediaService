package com.movienchill.mediaservice.controller;

import com.movienchill.mediaservice.constants.Router;
import com.movienchill.mediaservice.service.media.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public String HelloWorld() {
        return "Hello World !";
    }
}
