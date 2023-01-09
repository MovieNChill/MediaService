package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformDAO extends JpaRepository<Platform, Long> {

}
