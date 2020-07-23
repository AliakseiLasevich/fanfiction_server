package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Tag;
import com.fanfiction.webproject.ui.model.response.TagsCommonRest;

import java.util.List;

public interface TagService {
    Tag findOrSave(String name);

    List<Tag> findAll();

    Tag findByName(String name);

    TagsCommonRest findCommonTags();
}
