package com.movienchill.mediaservice.domain.specification.search;

import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private boolean orPredicate;
}
