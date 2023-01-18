package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Media;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaDAO extends JpaRepository<Media, Long> {
    List<Media> findAll(Specification<String> spec, Pageable pageable);
}
