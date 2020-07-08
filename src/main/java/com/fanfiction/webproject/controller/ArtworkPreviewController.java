package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.dto.ArtworkPreviewPageDto;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.mappers.ArtworkPreviewMapper;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.ui.model.response.ArtworkPreviewPageRest;
import com.fanfiction.webproject.ui.model.response.ArtworkPreviewRest;
import com.fanfiction.webproject.ui.model.response.ArtworkRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtworkPreviewController {

    @Autowired
    private ArtworkService artworkService;

    @GetMapping(value = "/artworksPreviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArtworkPreviewPageRest getArtworksCards(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "limit", defaultValue = "5") int limit) {
        ArtworkPreviewPageDto artworksPreviewPageDto = artworkService.getArtworksPreviewPage(page, limit);

        List<ArtworkPreviewRest> previewsPage = artworksPreviewPageDto
                .getArtworkDtoList()
                .stream()
                .map(ArtworkPreviewMapper.INSTANCE::dtoToArtworkPreviewRest)
                .collect(Collectors.toList());

        return new ArtworkPreviewPageRest(previewsPage, artworksPreviewPageDto.getPagesCount());
    }

    @GetMapping(path = "/users/{userId}/artworks")
    public ArtworkPreviewPageRest getArtworksPreviewsByUserId(@PathVariable String userId,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "limit", defaultValue = "5") int limit) {
        ArtworkPreviewPageDto artworksPreviewPageDto = artworkService.getArtworksPreviewPageByUserId(userId, page, limit);

        List<ArtworkDto> artworkDtos = artworkService.findByUserId(userId);

        List<ArtworkPreviewRest> previewsPage = artworkDtos.stream()
                .map(ArtworkPreviewMapper.INSTANCE::dtoToArtworkPreviewRest)
                .collect(Collectors.toList());

        return new ArtworkPreviewPageRest(previewsPage, artworksPreviewPageDto.getPagesCount());
    }
}
