package com.bard.notes.personalnotesapplication.ui.notes;

import com.bard.notes.personalnotesapplication.model.Note;
import com.bard.notes.personalnotesapplication.model.Tag;
import com.bard.notes.personalnotesapplication.services.NotesService;
import com.bard.notes.personalnotesapplication.ui.ConfirmDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;


public class NotesViewCard extends ListItem {

    private Note note;


    public NotesViewCard(Note note) {

        this.note = note;
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);



        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(note.getName());





        this.addClickListener((e) -> {
            Optional<UI> currentUi = getUI();
            currentUi.ifPresent(ui -> ui.navigate(NotesForm.VIEW_NAME + "/" + note.getNoteId()));
        });


        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);

        Paragraph description = new Paragraph(
                note.getShortDescription());
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");

        String tags = note.getTags().stream().map(Tag::getTag).collect(Collectors.joining(","));
        badge.setText(tags);

        add(header, subtitle, description, badge);

    }


}
