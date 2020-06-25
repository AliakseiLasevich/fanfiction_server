package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.ui.model.response.ArtworkRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @GetMapping("/artworks")
    public List<ArtworkRest> findAllArtworks() {
        List<ArtworkDto> artworkDtos = artworkService.findAll();
        return artworkDtos.stream()
                .map(ArtworkMapper.INSTANCE::dtoToRest)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/artworks/{artworkId}")
    public ArtworkRest getArtworkById(@PathVariable String artworkId) {
        ArtworkDto artworkDto = artworkService.findById(artworkId);
        return ArtworkMapper.INSTANCE.dtoToRest(artworkDto);
    }


    @GetMapping(path = "/users/{userId}/artworks/")
    public List<ArtworkRest> getArtworksByUserId(@PathVariable String userId) {
        List<ArtworkDto> artworkDtos = artworkService.findByUserId(userId);
        return artworkDtos.stream()
                .map(ArtworkMapper.INSTANCE::dtoToRest)
                .collect(Collectors.toList());
    }

}
