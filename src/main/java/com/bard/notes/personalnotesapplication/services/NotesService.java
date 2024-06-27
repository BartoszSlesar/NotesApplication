package com.bard.notes.personalnotesapplication.services;


import com.bard.notes.personalnotesapplication.model.Note;
import com.bard.notes.personalnotesapplication.repositories.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotesService {
    private final NotesRepository notesRepository;

    public void saveNote(Note note) {
        if (note != null && !note.getContent().isEmpty() && !note.getName().isEmpty()) {
            this.notesRepository.save(note);
        }
    }

    public List<Note> getNotes() {
        Iterable<Note> notesIterable = this.notesRepository.findAll();
        return Streamable.of(notesIterable).toList();
    }

    public void updateNote(Note note) {
        saveNote(note);
    }

    public boolean deleteNote(Long id) {
        return this.notesRepository.deleteByNoteId(id);
    }
}
