package com.bard.notes.personalnotesapplication.ui.notes;


import com.bard.notes.personalnotesapplication.model.Note;
import com.bard.notes.personalnotesapplication.model.Tag;
import com.bard.notes.personalnotesapplication.services.NotesService;
import com.bard.notes.personalnotesapplication.ui.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Notes")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class NotesView extends Main implements HasComponents, HasStyle {

    private OrderedList notesContainer;

    private final NotesService notesService;


    public static final String VIEW_NAME = "Notes";
    private TextField filter;
    private Button newNote;

    public NotesView(NotesService notesService) {
        this.notesService = notesService;
        constructUI();
        this.notesService.getNotes().forEach(
                note -> notesContainer.add(new NotesViewCard(note))
        );


    }


    private void constructUI() {
        addClassNames("notes-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);


        final HorizontalLayout topLayout = createTopBar();

        notesContainer = new OrderedList();
        notesContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(topLayout);
        add(container, notesContainer);

    }

    private HorizontalLayout createTopBar() {

        filter = new TextField();
        filter.setPlaceholder("Filter tags, separated by space, eg. technology learning");
        // Apply the filter to grid's data provider. TextField value is never
        filter.addValueChangeListener(
                event -> {
                    String tags = event.getValue();
                    if (tags.isEmpty()) {
                        notesContainer.removeAll();
                        this.notesService.getNotes().forEach(
                                note -> notesContainer.add(new NotesViewCard(note))
                        );
                    } else {

                        String[] tagsArray = new String[]{tags};
                        if (tags.contains(" ")) {
                            tagsArray = tags.split(" ");
                        }

                        List<Note> notes = this.notesService.getNotesByTags(tagsArray);
                        if (!notes.isEmpty()) {
                            notesContainer.removeAll();
                            notes.forEach(
                                    note -> notesContainer.add(new NotesViewCard(note))
                            );
                        }
                    }


                });
        // A shortcut to focus on the textField by pressing ctrl + F
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        newNote = new Button("New Note");
        // Setting theme variant of new production button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newNote.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newNote.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newNote.addClickListener(click -> getUI().orElseThrow(() -> new RuntimeException("Error")).navigate(NotesForm.VIEW_NAME));
        // A shortcut to click the new product button by pressing ALT + N
        newNote.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newNote);
        topLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, filter);
        topLayout.expand(filter);
        topLayout.addClassNames(Margin.Bottom.LARGE, Margin.Top.XLARGE);
        return topLayout;
    }

}
