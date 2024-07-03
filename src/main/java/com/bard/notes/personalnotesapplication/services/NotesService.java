package com.bard.notes.personalnotesapplication.services;


import com.bard.notes.personalnotesapplication.model.Note;
import com.bard.notes.personalnotesapplication.model.Tag;
import com.bard.notes.personalnotesapplication.repositories.NotesRepository;
import com.bard.notes.personalnotesapplication.repositories.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class NotesService {
    private final NotesRepository notesRepository;
    private final TagsRepository tagsRepository;


    public void saveNote(Note note) {
        if (note != null && !note.getContent().isEmpty() && !note.getName().isEmpty()) {
            this.notesRepository.save(note);
        }
    }

    @Transactional
    public List<Note> getNotes() {
        Iterable<Note> notesIterable = this.notesRepository.findAll();
        return Streamable.of(notesIterable).toList();
    }

    @Transactional
    public List<Note> getNotesByTags(String[] tags) {
        if (tags != null) {
            Iterable<Note> notesIterable = this.notesRepository.findAllByTags(Arrays.asList(tags));
            return Streamable.of(notesIterable).toList();
        }
        return new ArrayList<>();

    }

    @Transactional
    public Optional<Note> getNoteById(long id) {
        return this.notesRepository.findById(id);
    }

    @Transactional
    public void updateNote(Note note) {
        saveNote(note);
    }


    @Transactional
    public void deleteNote(Long id) {
        this.notesRepository.deleteById(id);
    }


    private List<Tag> getTags() {
        Tag tag = new Tag();
        tag.setTag("Technologia");
        Tag tag2 = new Tag();
        tag2.setTag("Nauka");
        Tag tag3 = new Tag();
        tag3.setTag("Marzenia");
        Tag tag4 = new Tag();
        tag4.setTag("Przyszłość");
        return List.of(tag, tag2, tag3, tag4);
    }

    private List<Note> generateNotes() {
        List<Tag> tags = getTags();
        Note note1 = new Note("Note1", "Snow mountains under stars", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.", tags);
        Note note2 = new Note("Note2", "Snow covered mountain", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.", tags);
        Note note3 = new Note("Note3", "River between mountains", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.", tags);
        Note note4 = new Note("Note4", "Milky way on mountains", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.", tags);
        Note note5 = new Note("Note5", "Mountain with fog", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.", tags);
        Note note6 = new Note("Note6", "Mountain at night", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.", tags);

        return List.of(note1, note2, note3, note4, note5, note6);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseWithDummyData() {
        List<Note> notes = generateNotes();
        this.notesRepository.saveAll(notes);
    }


}
