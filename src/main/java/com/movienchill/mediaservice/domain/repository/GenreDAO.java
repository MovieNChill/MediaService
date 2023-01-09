package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Genre;
import com.movienchill.mediaservice.domain.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreDAO extends JpaRepository<Genre, Long> {
    /**
     * Method to retrieve all genre of a Media
     * @param media the media
     * @return The list of genre
     */
    List<Genre> findAllByMedia(Media media);

    Genre findAllByGenre(String genre);
}
