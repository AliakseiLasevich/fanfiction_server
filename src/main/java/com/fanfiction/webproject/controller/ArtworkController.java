package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.exceptions.UserServiceException;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.ui.model.request.ArtworkRequestModel;
import com.fanfiction.webproject.ui.model.response.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @GetMapping(path = "/artworks/{artworkId}")
    public ArtworkRest getArtworkById(@PathVariable String artworkId) {
        ArtworkDto artworkDto = artworkService.findById(artworkId);
        return ArtworkMapper.INSTANCE.dtoToArtworkRest(artworkDto);
    }

    @PostMapping(path = "/users/{userId}/artworks", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ArtworkRest createArtwork(@RequestBody ArtworkRequestModel artworkRequestModel,
                                     @PathVariable String userId) {
        checkRequestModel(artworkRequestModel);
        ArtworkDto artworkDto = convertRequestToDto(artworkRequestModel, userId);
        ArtworkDto createdArtwork = artworkService.createArtwork(artworkDto);
        return ArtworkMapper.INSTANCE.dtoToArtworkRest(createdArtwork);
    }

    @PutMapping(path = "/users/{userId}/artworks/{artworkId}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ArtworkRest updateArtwork(@RequestBody ArtworkRequestModel artworkRequestModel,
                                     @PathVariable String userId,
                                     @PathVariable String artworkId) {
        checkRequestModel(artworkRequestModel);
        ArtworkDto artworkDto = convertRequestToDto(artworkRequestModel, userId);
        ArtworkDto updated = artworkService.update(artworkDto, artworkId);
        return ArtworkMapper.INSTANCE.dtoToArtworkRest(updated);
    }

    @GetMapping(path = "/top")
    public List<ArtworkPreviewRest> getTopArtworksByRating(@RequestParam int limit) {
        List<ArtworkDto> topArtworks = artworkService.findTopOrderByAvg(limit);
        return topArtworks.stream()
                .map(ArtworkMapper.INSTANCE::dtoToArtworkPreviewRest)
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/{userId}/{artworkId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.userId")
    public OperationStatusModel deleteArtwork(@PathVariable String artworkId, @PathVariable String userId) {
        artworkService.deleteArtwork(artworkId);
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return operationStatusModel;
    }

    private ArtworkDto convertRequestToDto(@RequestBody ArtworkRequestModel artworkRequestModel, @PathVariable String userId) {
        ArtworkDto artworkDto = ArtworkMapper.INSTANCE.requestModelToDto(artworkRequestModel);
        artworkDto.setUserId(userId);
        return artworkDto;
    }

    private void checkRequestModel(ArtworkRequestModel artworkRequestModel) {
        if (!ObjectUtils.allNotNull(
                artworkRequestModel.getName(),
                artworkRequestModel.getSummary(),
                artworkRequestModel.getChapters(),
                artworkRequestModel.getGenre(),
                artworkRequestModel.getTags()
        )) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }

}
