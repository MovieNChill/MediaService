package com.movienchill.mediaservice.domain.specification;

import com.movienchill.mediaservice.domain.specification.search.SearchCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
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
            // Operation decrease
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
            // Operator like
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            // try {
            if (criteria.getKey() == "*") {
                SearchCriteria nameSearchCriteria = new SearchCriteria("name", ":", criteria.getValue());
                SearchCriteria directorSearchCriteria = new SearchCriteria("director", ":", criteria.getValue());
                SearchCriteria descriptionSearchCriteria = new SearchCriteria("description", ":", criteria.getValue());

                Predicate namePredicate = format(root, nameSearchCriteria, builder);
                Predicate directorPredicate = format(root, directorSearchCriteria, builder);
                Predicate descriptionPredicate = format(root, descriptionSearchCriteria, builder);

                query.where(builder.or(namePredicate, directorPredicate, descriptionPredicate));

                Order[] orderList = {
                        builder.desc(format(root, nameSearchCriteria, builder)),
                        builder.desc(format(root, directorSearchCriteria, builder)),
                        builder.desc(format(root, descriptionSearchCriteria, builder)),
                };

                query.orderBy(orderList);

                return query.getRestriction();
            }
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return format(root, this.criteria, builder);
            } else {
                // Operator equal
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
            // } catch (IllegalArgumentException e) {
            // i
            // }
        }
        return null;
    }

    private Predicate format(Root<String> root, SearchCriteria searchCriteria, CriteriaBuilder builder) {
        return builder.like(
                builder.lower(
                        builder.function("replace", String.class,
                                builder.function("replace", String.class,
                                        builder.function("replace", String.class,
                                                root.get(searchCriteria.getKey()),
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
    }
}