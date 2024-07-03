package com.bard.notes.personalnotesapplication.repositories;

import com.bard.notes.personalnotesapplication.model.Note;
import com.bard.notes.personalnotesapplication.model.Tag;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NotesRepository extends CrudRepository<Note, Long> {


    @Query("SELECT EXISTS(SELECT 1 FROM Note WHERE noteId = :id)")
    boolean checkIfExist(@Param(value = "id") Long id);

    @Query(value = "SELECT n.note_id, n.content, n.name, n.short_description FROM notes n JOIN notes_tags nt ON n.note_id = nt.note_id JOIN tags t ON nt.tag_id = t.tag_id WHERE t.tag IN :tagList", nativeQuery = true)
    List<Note> findAllByTags(List<String> tagList);


}
