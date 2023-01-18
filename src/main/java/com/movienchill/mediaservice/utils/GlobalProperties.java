package com.movienchill.mediaservice.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GlobalProperties {
    @Value("${url.recommend}")
    private String urlRecommend;

    @Value("${api.tmdb.url}")
    private String urlTmdb;

    @Value("${api.tmdb.key}")
    private String apiKey;
}
