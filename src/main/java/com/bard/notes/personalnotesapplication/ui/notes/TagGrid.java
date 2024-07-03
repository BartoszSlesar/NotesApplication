package com.bard.notes.personalnotesapplication.ui.notes;

import com.bard.notes.personalnotesapplication.model.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class TagGrid extends Div {

    private List<Tag> tags;
    private Grid<Tag> grid;

    public TagGrid() {
        this(new ArrayList<>());
    }

    public TagGrid(List<Tag> tags) {
        this.tags = tags;
        this.grid = new Grid<>(Tag.class, false);
        this.grid.setClassName("force-focus-outline");
        this.grid.addColumn(Tag::getTag).setKey("Tag")
                .setHeader("Tags");
        this.grid.setItems(tags);


        final HorizontalLayout addTagLayout = new HorizontalLayout();
        TextField tagName = new TextField();

        Button add = new Button("add Tag");
        add.addClickListener(event -> {
            if (!tagName.getValue().isEmpty()) {
                Tag tag = new Tag();
                tag.setTag(tagName.getValue());
                this.tags.add(tag);
                grid.getDataProvider().refreshAll();
                this.tags.forEach(System.out::println);
            }

        });


        Button remove = new Button("remove Tag");
        remove.addClickListener(buttonClickEvent -> {
            if (!grid.asSingleSelect().isEmpty()) {
                this.tags.remove(grid.asSingleSelect().getValue());
                grid.getDataProvider().refreshAll();
            }


        });


        addTagLayout.add(tagName, add, remove);
        add(grid, addTagLayout);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
        this.grid.setItems(tags);
//        this.grid.getDataProvider().refreshAll();
    }

}
