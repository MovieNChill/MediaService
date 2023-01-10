package com.movienchill.mediaservice.domain.specification.builder;

import com.movienchill.mediaservice.domain.specification.SpecificationSearch;
import com.movienchill.mediaservice.domain.specification.search.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder {
    private final List<SearchCriteria> params;

    public SpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final SpecificationBuilder with(String key, String operation, Object value) {
        return with(key, operation, value);
    }

    public Specification build() {
        if (params.size() == 0)
            return null;

        Specification result = new SpecificationSearch(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new SpecificationSearch(params.get(i)))
                    : Specification.where(result).and(new SpecificationSearch(params.get(i)));
        }

        return result;
    }
}
