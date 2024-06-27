package com.bard.notes.personalnotesapplication.repositories;

import com.bard.notes.personalnotesapplication.model.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NotesRepository extends CrudRepository<Note,Long> {



    @Query("SELECT EXISTS(SELECT 1 FROM Note WHERE noteId = :id)")
    boolean checkIfExist(@Param(value = "id") Long id);

    boolean deleteByNoteId(Long id);
}
