package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.RatingDto;
import com.fanfiction.webproject.entity.Rating;
import com.fanfiction.webproject.exceptions.RatingServiceException;
import com.fanfiction.webproject.mappers.RatingMapper;
import com.fanfiction.webproject.repository.RatingRepository;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.service.interfaces.RatingService;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ArtworkService artworkService;
    private final UserService userService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, ArtworkService artworkService, UserService userService) {
        this.ratingRepository = ratingRepository;
        this.artworkService = artworkService;
        this.userService = userService;
    }

    @Override
    public RatingDto getAverageRating(String artworkId) {
        double d = ratingRepository.getAverageRatingByArtworkId(artworkId);
        RatingDto ratingDto = new RatingDto();
        ratingDto.setValue(d);
        return ratingDto;
    }

    @Override
    public RatingDto getRatingBy(String userId, String artworkId) {
        Rating rating = ratingRepository.findByUserEntityUserIdAndArtworkArtworkId(userId, artworkId);
        return RatingMapper.INSTANCE.entityToDto(rating);
    }

    @Override
    public RatingDto createRating(String userId, String artworkId, double value) {
        if (ratingRepository.findByUserEntityUserIdAndArtworkArtworkId(userId, artworkId) != null) {
            throw new RatingServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
        Rating r = new Rating();
        r.setArtwork(artworkService.findArtworkEntityByArtworkId(artworkId));
        r.setUserEntity(userService.getUserEntityByUserId(userId));
        r.setValue(value);
        Rating saved = ratingRepository.save(r);
        return RatingMapper.INSTANCE.entityToDto(saved);
    }
}
