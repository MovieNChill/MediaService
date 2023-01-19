package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Star;
import com.movienchill.mediaservice.domain.model.Media;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarDAO extends JpaRepository<Star, Long> {
    Star findByName(String name);
}
