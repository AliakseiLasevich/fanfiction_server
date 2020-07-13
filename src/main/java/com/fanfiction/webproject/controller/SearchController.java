package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.service.HibernateSearchService;
import com.fanfiction.webproject.ui.model.response.ArtworkPreviewRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private HibernateSearchService searchService;

    @GetMapping
    public List<ArtworkPreviewRest> search(@RequestParam(value = "search", required = false) String q) {
        List<ArtworkDto> searchResults = null;

        try {
            searchResults = searchService.fuzzySearch(q);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return searchResults.stream()
                .map(ArtworkMapper.INSTANCE::dtoToArtworkPreviewRest)
                .collect(Collectors.toList());

    }
}
