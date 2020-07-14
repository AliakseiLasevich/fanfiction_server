package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.Genre;
import com.fanfiction.webproject.repository.GenreRepository;
import com.fanfiction.webproject.service.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    GenreRepository genreRepository;

    @Override
    public Genre findOrSave(String name) {
        Genre genre = genreRepository.findByName(name);
        if (genre == null) {
            genre = new Genre();
            genre.setName(name);
            genreRepository.save(genre);
        }
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
