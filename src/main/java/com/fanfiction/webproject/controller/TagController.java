package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.entity.Tag;
import com.fanfiction.webproject.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    private List<Tag> findAllTags() {
        return tagService.findAll();
    }
}
