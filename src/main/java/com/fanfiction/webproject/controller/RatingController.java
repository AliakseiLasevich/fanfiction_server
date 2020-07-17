package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.RatingDto;
import com.fanfiction.webproject.exceptions.UserServiceException;
import com.fanfiction.webproject.mappers.RatingMapper;
import com.fanfiction.webproject.service.interfaces.RatingService;
import com.fanfiction.webproject.ui.model.request.RatingRequestModel;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.fanfiction.webproject.ui.model.response.RatingRest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping(path = "/{artworkId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RatingRest getArtworkAverageRating(@PathVariable String artworkId) {
        RatingDto ratingDto = ratingService.getAverageRating(artworkId);
        return RatingMapper.INSTANCE.dtoToRest(ratingDto);
    }

    @GetMapping(path = "/{artworkId}/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RatingRest getUserArtworkRating(@PathVariable String userId,
                                            @PathVariable String artworkId) {
        RatingDto ratingDto = ratingService.getRatingBy(userId, artworkId);
        return RatingMapper.INSTANCE.dtoToRest(ratingDto);
    }

    @PreAuthorize("#userId == principal.userId")
    @PostMapping(path = "/{artworkId}/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RatingRest createArtwork(
            @RequestBody RatingRequestModel requestModel,
            @PathVariable String userId,
            @PathVariable String artworkId) {
        if (!ObjectUtils.allNotNull(
                requestModel,
                userId,
                artworkId
        )) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        RatingDto ratingDto = ratingService.createRating(userId, artworkId, requestModel.getValue());
        return RatingMapper.INSTANCE.dtoToRest(ratingDto);
    }
}
