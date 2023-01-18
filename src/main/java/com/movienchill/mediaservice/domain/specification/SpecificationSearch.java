package com.movienchill.mediaservice.domain.specification;

import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.domain.specification.search.SearchCriteria;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.annotation.Annotation;
import java.util.List;

import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredField;
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
            if (criteria.getKey() == "*") {
                SearchCriteria nameSearchCriteria = new SearchCriteria("name", ":", criteria.getValue());
                SearchCriteria directorSearchCriteria = new SearchCriteria("director", ":", criteria.getValue());
                SearchCriteria descriptionSearchCriteria = new SearchCriteria("description", ":", criteria.getValue());

                Predicate namePredicate = format(nameSearchCriteria, root, query, builder, null);
                Predicate directorPredicate = format(directorSearchCriteria, root, query, builder, null);
                Predicate descriptionPredicate = format(descriptionSearchCriteria, root, query, builder, null);

                query.where(builder.or(namePredicate, directorPredicate, descriptionPredicate));

                Order[] orderList = {
                        builder.desc(format(nameSearchCriteria, root, query, builder, null)),
                        builder.desc(format(directorSearchCriteria, root, query, builder, null)),
                        builder.desc(format(descriptionSearchCriteria, root, query, builder, null)),
                };

                query.orderBy(orderList);

                return query.getRestriction();
            }
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return format(this.criteria, root, query, builder, null);
            } else {
                if (root.get(criteria.getKey()).getJavaType() == Genre.class) {
                    return format(criteria, root, query, builder, Genre.class);
                }
                // Operator equal
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
            // } catch (IllegalArgumentException e) {
            // i
            // }
        }
        return null;
    }

    private Predicate format(SearchCriteria searchCriteria, Root<String> root, CriteriaQuery<?> query,
            CriteriaBuilder builder, Class classType) {
        Path<String> path;
        if (classType == null) {
            path = root.get(searchCriteria.getKey());
        } else {
            Join<String, String> join = root.join(searchCriteria.getKey(), JoinType.LEFT);
            path = join.get(classType.getDeclaredFields()[1].getName());
        }
        return builder.like(
                builder.lower(
                        builder.function("replace", String.class,
                                builder.function("replace", String.class,
                                        builder.function("replace", String.class,
                                                path,
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