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
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(path = "/artworks/{artworkId}/average",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RatingRest getArtworkAverageRating(@PathVariable String artworkId) {
        RatingDto ratingDto = ratingService.getAverageRating(artworkId);
        return RatingMapper.INSTANCE.dtoToRest(ratingDto);
    }

    @GetMapping(path = "/artworks/{artworkId}/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RatingRest getUserArtworkRating(@PathVariable String artworkId, @PathVariable String userId) {
        RatingDto ratingDto = ratingService.getRatingBy(userId, artworkId);
        return RatingMapper.INSTANCE.dtoToRest(ratingDto);
    }

    @PreAuthorize("#userId == principal.userId")
    @PostMapping(path = "/artworks/{artworkId}/users/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RatingRest createRating(@RequestBody RatingRequestModel requestModel, @PathVariable String artworkId, @PathVariable String userId) {
        validateRequestModel(requestModel, artworkId);
        RatingDto ratingDto = ratingService.createRating(userId, artworkId, requestModel.getValue());
        return RatingMapper.INSTANCE.dtoToRest(ratingDto);
    }



    private void validateRequestModel(@RequestBody RatingRequestModel requestModel, @PathVariable String artworkId) {
        if (!ObjectUtils.allNotNull(requestModel, artworkId)) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }
}
