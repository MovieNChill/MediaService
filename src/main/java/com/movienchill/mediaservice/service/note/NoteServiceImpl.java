package com.movienchill.mediaservice.service.note;

import com.movienchill.mediaservice.domain.dto.NoteDTO;
import com.movienchill.mediaservice.domain.model.Note;
import com.movienchill.mediaservice.service.IGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoteServiceImpl implements NoteService, IGenericService<Note, NoteDTO> {

    @Override
    public List<NoteDTO> findAll() {
        return null;
    }

    @Override
    public NoteDTO findById(Long id) {
        return null;
    }

    @Override
    public void save(Note entity) {
    }

    @Override
    public void delete(Note entity) {

    }
}
