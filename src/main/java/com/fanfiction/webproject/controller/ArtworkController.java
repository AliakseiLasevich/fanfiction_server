package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.exceptions.UserServiceException;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.ui.model.request.ArtworkRequestModel;
import com.fanfiction.webproject.ui.model.response.ArtworkRest;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/users/{userId}/artworks", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ArtworkRest createArtwork(@RequestBody ArtworkRequestModel artworkRequestModel,
                                     @PathVariable String userId) {
        if (!ObjectUtils.allNotNull(
                artworkRequestModel.getChapters(),
                artworkRequestModel.getGenre(),
                artworkRequestModel.getSummary(),
                artworkRequestModel.getTags())) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        ArtworkDto artworkDto = ArtworkMapper.INSTANCE.requestModelToDto(artworkRequestModel);
        artworkDto.setUserId(userId);
        ArtworkDto createdArtwork = artworkService.createArtwork(artworkDto);
        return ArtworkMapper.INSTANCE.dtoToRest(createdArtwork);
    }

}
