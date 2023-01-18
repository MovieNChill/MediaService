package com.movienchill.mediaservice.domain.specification;

import com.movienchill.mediaservice.domain.specification.search.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationSearch implements Specification<String> {
    private SearchCriteria criteria;

    public SpecificationSearch(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<String> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // Operator sup
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            // Operation decrease
            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":") || criteria.getOperation().equalsIgnoreCase("+")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                // Operator like
                return builder.like(
                        builder.lower(
                                builder.function("replace", String.class,
                                        builder.function("replace", String.class,
                                                builder.function("replace", String.class,
                                                        root.get(criteria.getKey()),
                                                        builder.literal(" "),
                                                        builder.literal("")),
                                                builder.literal("-"),
                                                builder.literal("")),
                                        builder.literal("-"),
                                        builder.literal(""))),
                        "%" + criteria.getValue().toString().toLowerCase()
                                .replace("-", "")
                                .replace(" ", "")
                                .replace("_", "")
                                + "%");
            } else {
                // Operator equal
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}