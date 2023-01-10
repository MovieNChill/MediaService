package com.movienchill.mediaservice.domain.specification.builder;

import com.movienchill.mediaservice.domain.specification.SpecificationSearch;
import com.movienchill.mediaservice.domain.specification.search.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {
    private final List<SearchCriteria> params;

    public SpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final SpecificationBuilder<T> with(String key, String operation, Object value) {
        return with(false, key, operation, value);
    }

    public final SpecificationBuilder<T> with(final boolean orPredicate, final String key, final String operation, final Object value) {
        params.add(new SearchCriteria(key, operation, value, orPredicate));
        return this;
    }

    public Specification<T> build() {
        if (params.size() == 0)
            return null;

        Specification<T> result = new SpecificationSearch(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new SpecificationSearch(params.get(i)))
                    : Specification.where(result).and(new SpecificationSearch(params.get(i)));
        }

        return result;
    }
}
