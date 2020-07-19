package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.LikeDto;
import com.fanfiction.webproject.exceptions.UserServiceException;
import com.fanfiction.webproject.mappers.LikeMapper;
import com.fanfiction.webproject.service.interfaces.LikeService;
import com.fanfiction.webproject.ui.model.request.LikeRequestModel;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.fanfiction.webproject.ui.model.response.LikeRest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;


    @GetMapping(path = "/{userId}/{artworkId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LikeDto> getUserLikesByArtworkIdAndChapterNumber(@PathVariable String userId,
                                                                 @PathVariable String artworkId) {
        List<LikeDto> likeDtos = likeService.getLikesBy(userId, artworkId);
        return likeDtos;
    }

    @PreAuthorize("#userId == principal.userId")
    @PostMapping(path = "/{userId}/{artworkId}/{chapterNumber}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LikeRest postLike(@RequestBody LikeRequestModel requestModel,
                             @PathVariable String userId,
                             @PathVariable String artworkId,
                             @PathVariable int chapterNumber) {
        validateLikeRequestModel(requestModel, userId, artworkId, chapterNumber);
        LikeDto likeDto = likeService.create(userId, artworkId, chapterNumber, requestModel.isLike());
        return LikeMapper.INSTANCE.dtoToRest(likeDto);
    }


    @PreAuthorize("#userId == principal.userId")
    @PutMapping(path = "/{userId}/{artworkId}/{chapterNumber}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public LikeRest putLike(@RequestBody LikeRequestModel requestModel,
                            @PathVariable String userId,
                            @PathVariable String artworkId,
                            @PathVariable int chapterNumber) {
        validateLikeRequestModel(requestModel, userId, artworkId, chapterNumber);
        LikeDto likeDto = likeService.update(userId, artworkId, chapterNumber, requestModel.isLike());
        return LikeMapper.INSTANCE.dtoToRest(likeDto);
    }

    private void validateLikeRequestModel(LikeRequestModel requestModel,
                                          String userId,
                                          String artworkId,
                                          int chapterNumber) {
        if (!ObjectUtils.allNotNull(
                requestModel,
                userId,
                artworkId,
                chapterNumber
        )) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }
}
