package com.movienchill.mediaservice.domain.metamodel;

import com.movienchill.mediaservice.domain.model.Media;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Media.class)

public class Media_ {

    public static volatile SingularAttribute<Media, Long> id;

    public static volatile SingularAttribute<Media, String> writers;
    public static volatile SingularAttribute<Media, String> stars;
}
