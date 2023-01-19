package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Writer;
import com.movienchill.mediaservice.domain.model.Media;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterDAO extends JpaRepository<Writer, Long> {
    Writer findByName(String name);
}
