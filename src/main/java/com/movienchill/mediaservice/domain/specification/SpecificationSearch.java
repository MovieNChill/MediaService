package com.movienchill.mediaservice.domain.specification;

import com.movienchill.mediaservice.domain.metamodel.Media_;
import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.domain.model.Media;
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
import jakarta.persistence.metamodel.Bindable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredField;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationSearch implements Specification<Media> {
    private SearchCriteria criteria;

    public SpecificationSearch(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Media> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            // Operator like
            if (criteria.getKey() == "*") {
                SearchCriteria nameSearchCriteria = new SearchCriteria("name", ":", criteria.getValue());
                SearchCriteria directorSearchCriteria = new SearchCriteria("director", ":", criteria.getValue());
                SearchCriteria descriptionSearchCriteria = new SearchCriteria("description", ":", criteria.getValue());

                Predicate namePredicate = format(nameSearchCriteria, root, query, builder);
                Predicate directorPredicate = format(directorSearchCriteria, root, query, builder);
                Predicate descriptionPredicate = format(descriptionSearchCriteria, root, query, builder);

                query.where(builder.or(namePredicate, directorPredicate, descriptionPredicate));

                Order[] orderList = {
                        builder.desc(format(nameSearchCriteria, root, query, builder)),
                        builder.desc(format(directorSearchCriteria, root, query, builder)),
                        builder.desc(format(descriptionSearchCriteria, root, query, builder)),
                };

                query.orderBy(orderList);

                return query.getRestriction();
            }
            return format(criteria, root, query, builder);
            // Operator equal
            // return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }

    private Predicate format(SearchCriteria searchCriteria, Root<Media> root, CriteriaQuery<?> query,
            CriteriaBuilder builder) {
        Path<Media> path;
        Bindable<Object> classType = (root.get(searchCriteria.getKey()).getModel());
        System.out.println(root.get(searchCriteria.getKey()));
        if (classType.toString().contains("MANY_TO_MANY"))
            path = root.join(searchCriteria.getKey(), JoinType.LEFT)
                    .get(root.get(searchCriteria.getKey()).getJavaType().getDeclaredFields()[1].getName());
        else
            path = root.get(searchCriteria.getKey());
        String string = "%" + criteria.getValue().toString().toLowerCase()
                .replace("-", "")
                .replace(" ", "")
                .replace("_", "")
                + "%";
        Expression<String> expression = builder.lower(builder.function("replace", String.class,
                builder.function("replace", String.class,
                        builder.function("replace", String.class,
                                path,
                                builder.literal(" "),
                                builder.literal("")),
                        builder.literal("-"),
                        builder.literal("")),
                builder.literal("-"),
                builder.literal("")));

        if (classType.toString().contains("ELEMENT_COLLECTION")) {
            return builder.isMember(searchCriteria.getValue(),
                    root.get(searchCriteria.getKey()));
        }
        // return builder.isMember(searchCriteria.getValue(),
        // root.get(searchCriteria.getKey()));
        return builder.like(expression, string);
    }
}