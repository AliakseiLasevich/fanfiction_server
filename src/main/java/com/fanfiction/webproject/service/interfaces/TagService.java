package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Tag;

public interface TagService {
    Tag findOrSave(String name);
}
