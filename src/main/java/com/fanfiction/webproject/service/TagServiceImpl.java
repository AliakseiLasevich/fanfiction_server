package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.Tag;
import com.fanfiction.webproject.repository.TagRepository;
import com.fanfiction.webproject.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag findOrSave(String name) {
        Tag tag = tagRepository.findByName(name);
        if (tag == null) {
            tag = new Tag();
            tag.setName(name);
            tagRepository.save(tag);
        }
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }
}
