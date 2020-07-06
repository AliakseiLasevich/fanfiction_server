package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Genre;

public interface GenreService {
    Genre findOrSave(String name);
}
