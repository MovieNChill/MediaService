package com.movienchill.mediaservice.domain.dto;

import lombok.Data;

@Data
public class RecommendationDTO {
    private Long user_id;
    private String desired_genre;
}
