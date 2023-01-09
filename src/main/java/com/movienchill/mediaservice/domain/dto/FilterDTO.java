package com.movienchill.mediaservice.domain.dto;

import lombok.Data;

@Data
public class FilterDTO {
    private Integer numberPage;
    private Integer numberElement;
    private String sort;
    private String name;
    private String genre;
}
