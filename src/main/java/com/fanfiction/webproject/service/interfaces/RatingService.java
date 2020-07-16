package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.RatingDto;

public interface RatingService {

    RatingDto getAverageRating(String artworkId);

    RatingDto getRatingBy(String userId, String artworkId);

    RatingDto createRating(String userId, String artworkId, int value);
}
