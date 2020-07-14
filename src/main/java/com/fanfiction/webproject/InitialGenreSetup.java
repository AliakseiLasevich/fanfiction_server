package com.fanfiction.webproject;

import com.fanfiction.webproject.service.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialGenreSetup {

    @Autowired
    private GenreService genreService;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        genreService.findOrSave("Action");
        genreService.findOrSave("Cyberpunk");
        genreService.findOrSave("Horror");
        genreService.findOrSave("Drama");
        genreService.findOrSave("Fantasy");
        genreService.findOrSave("Erotic");
        genreService.findOrSave("Mystery");
        genreService.findOrSave("Thriller");
    }

}
