package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.Tag;
import com.fanfiction.webproject.repository.TagRepository;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.service.interfaces.TagService;
import com.fanfiction.webproject.ui.model.response.TagsCommonRest;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final ArtworkService artworkService;

    public TagServiceImpl(TagRepository tagRepository, @Lazy ArtworkService artworkService) {
        this.tagRepository = tagRepository;
        this.artworkService = artworkService;
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag findOrSave(String name) {
        Tag tag = findByName(name);
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

    @Override
    public TagsCommonRest findCommonTags() {
        List<Tag> allTags = tagRepository.findAll();
        Map<String, Integer> tagsFrequency = getTagsFrequency(allTags);
        return new TagsCommonRest(sortTagsByFrequency(tagsFrequency));
    }

    private Map<String, Integer> getTagsFrequency(List<Tag> allTags) {
        Map<String, Integer> tagsFrequency = new HashMap<>();
        allTags.forEach(tag -> {
            int frequency = artworkService.getArtworksByTag(tag.getName()).size();
            tagsFrequency.put(tag.getName(), frequency);
        });
        return tagsFrequency;
    }

    private Map<String, Integer> sortTagsByFrequency(Map<String, Integer> tagsFrequency) {
        tagsFrequency = tagsFrequency.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return tagsFrequency;
    }
}
