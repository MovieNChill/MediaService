package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreDAO extends JpaRepository<Genre, Long> {

}
