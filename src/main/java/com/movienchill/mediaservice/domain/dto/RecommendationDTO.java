package com.movienchill.mediaservice.domain.dto;

import lombok.Data;

@Data
public class RecommendationDTO {
    private Long userId;
    private String desired_genre;
}
