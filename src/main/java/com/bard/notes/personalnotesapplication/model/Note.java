package com.bard.notes.personalnotesapplication.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "notes")
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteId;
    private String name;
    private String shortDescription;
    private String content;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "notes_tags",
            joinColumns = { @JoinColumn(name = "note_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private List<Tag> tags;

    public Note() {
    }

    public Note(long noteId, String name, String shortDescription, String content, List<Tag> tags) {
        this.noteId = noteId;
        this.name = name;
        this.shortDescription = shortDescription;
        this.content = content;
        this.tags = tags;
    }

    public Note(String name, String shortDescription, String content, List<Tag> tags) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.content = content;
        this.tags = tags;
    }


}
