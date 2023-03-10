package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisDAO extends JpaRepository<Avis, Long> {

}
