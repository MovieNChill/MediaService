package com.movienchill.mediaservice.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlatformDTO {
    private String name;
    private List<String> country;
    public PlatformDTO(String name, List<String> country) {
        this.name = name;
        this.country = country;
    }
}
