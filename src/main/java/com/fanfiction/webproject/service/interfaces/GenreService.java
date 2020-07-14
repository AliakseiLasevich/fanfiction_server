package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Genre;

import java.util.List;

public interface GenreService {
    Genre findOrSave(String name);

    List<Genre> findAll();
}
