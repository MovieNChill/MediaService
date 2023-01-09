package com.movienchill.mediaservice.domain.dto;

import lombok.Data;

@Data
public class NoteDTO {
    private Long id;
    private Long idUser;
    private Long idMedia;
    private Float note;
}
