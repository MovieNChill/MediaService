package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaDAO extends JpaRepository<Media, Long> {

}
