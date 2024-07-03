package com.bard.notes.personalnotesapplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "personal-note-application", variant = Lumo.DARK)
public class PersonalNotesApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(PersonalNotesApplication.class, args);
    }

}
