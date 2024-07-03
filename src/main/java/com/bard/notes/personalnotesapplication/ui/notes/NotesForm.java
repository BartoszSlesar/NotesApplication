package com.bard.notes.personalnotesapplication.ui.notes;

import com.bard.notes.personalnotesapplication.model.Note;
import com.bard.notes.personalnotesapplication.services.NotesService;
import com.bard.notes.personalnotesapplication.ui.ConfirmDialog;
import com.bard.notes.personalnotesapplication.ui.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;

import java.util.Optional;

@PageTitle("editor")
@Route(value = "editor", layout = MainLayout.class)
public class NotesForm extends Div implements HasUrlParameter<Long> {

    public static final String VIEW_NAME = "editor";
    private final VerticalLayout content;

    private final TextField noteName;
    private final TextField shortDescription;
    private final TextArea noteContent;
    private Button save;
    private Button cancel;
    private final Button delete;

    private Note updateNote;

    private final NotesService notesService;

    private TagGrid tagGrid;

    public NotesForm(NotesService notesService) {
        this.notesService = notesService;
        setClassName("note-form");

        this.content = new VerticalLayout();
        this.content.setSizeUndefined();
        this.content.addClassName("note-form-content");
        add(this.content);

        this.noteName = new TextField("Note name");
        this.noteName.setWidth("50%");
        this.noteName.setRequired(true);
        this.noteName.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(this.noteName);

        this.shortDescription = new TextField("Short description");
        this.shortDescription.setWidth("50%");
        this.shortDescription.setValueChangeMode(ValueChangeMode.EAGER);
        this.content.add(this.shortDescription);

        this.noteContent = new TextArea("Note Content");
        this.noteContent.setWidth("50%");
        this.noteContent.setHeight("40%");
        this.noteContent.setValueChangeMode(ValueChangeMode.EAGER);
        this.content.add(this.noteContent);

        this.tagGrid = new TagGrid();
        this.content.add(this.tagGrid);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        this.save = new Button("Save");
//        save.setWidth("100%");
        this.save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.save.addClickListener(event -> {
            if (this.updateNote != null) {
                this.updateNote.setName(noteName.getValue());
                this.updateNote.setShortDescription(shortDescription.getValue());
                this.updateNote.setContent(noteContent.getValue());
                this.updateNote.setTags(tagGrid.getTags());
                tagGrid.getTags().forEach(System.out::println);
                this.notesService.updateNote(this.updateNote);
            } else {
                Note note = new Note();
                note.setName(noteName.getValue());
                note.setShortDescription(shortDescription.getValue());
                note.setContent(noteContent.getValue());
                note.setTags(tagGrid.getTags());
                this.notesService.saveNote(note);
            }
            getUI().get().navigate("");
        });
        this.save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);


        this.cancel = new Button("Cancel");
//        cancel.setWidth("100%");
        this.cancel.addClickListener(event -> {
            if (getUI().isPresent()) {
                getUI().get().navigate("");
            }
        });
        this.cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> System.out.println("cancel"))
                .setFilter("event.key == 'Escape'");

        this.delete = new Button("Delete");
        this.delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        this.delete.addClickListener(event -> {
            if (updateNote != null) {
                final ConfirmDialog dialog = new ConfirmDialog(
                        "Please confirm",
                        "Are you sure you want to delete the Note? ",
                        "Delete", () -> {
                    this.notesService.deleteNote(updateNote.getNoteId());
                    Optional<UI> currentUi = getUI();
                    currentUi.ifPresent(ui -> {
                        ui.navigate("");
                    });
                    Notification.show("Note Deleted.");
                });

                dialog.open();
            }
        });
        if (updateNote == null) {
            delete.setVisible(false);
        }
        buttonLayout.add(save, delete, cancel);

        content.add(buttonLayout);
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent,@OptionalParameter Long id) {
        if (id != null) {
            Optional<Note> note = this.notesService.getNoteById(id);
            note.ifPresent(value -> {
                this.updateNote = value;
                this.noteName.setValue(value.getName());
                this.shortDescription.setValue(value.getShortDescription());
                this.noteContent.setValue(value.getContent());
                this.tagGrid.setTags(value.getTags());
                value.getTags().forEach(System.out::println);
                this.delete.setVisible(true);
            });
        }
    }
}
