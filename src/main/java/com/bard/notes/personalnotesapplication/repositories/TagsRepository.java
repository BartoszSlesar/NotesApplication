package com.bard.notes.personalnotesapplication.repositories;


import com.bard.notes.personalnotesapplication.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagsRepository extends CrudRepository<Tag,Long> {
}
