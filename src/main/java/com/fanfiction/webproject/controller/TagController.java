package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.entity.Tag;
import com.fanfiction.webproject.service.interfaces.TagService;
import com.fanfiction.webproject.ui.model.response.TagsCommonRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> findAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/common")
    public TagsCommonRest findCommonTags() {
        return tagService.findCommonTags();
    }
}
