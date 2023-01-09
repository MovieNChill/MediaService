package com.movienchill.mediaservice.domain.repository;

import com.movienchill.mediaservice.domain.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDAO extends JpaRepository<Note, Long> {

}
