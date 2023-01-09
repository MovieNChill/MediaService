package com.movienchill.mediaservice.domain.dto;

import lombok.Data;

@Data
public class AvisDTO {
    private Long id;
    private Long idUser;
    private Long idMedia;
    private String avis;
}
