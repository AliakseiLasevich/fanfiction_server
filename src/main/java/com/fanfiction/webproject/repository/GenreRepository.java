package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
}
