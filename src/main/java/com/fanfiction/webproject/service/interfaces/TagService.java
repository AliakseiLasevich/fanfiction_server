package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Tag;

import java.util.List;

public interface TagService {
    Tag findOrSave(String name);

    List<Tag> findAll();
}
