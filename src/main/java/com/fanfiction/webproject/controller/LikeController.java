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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;


    @GetMapping(path = "/{userId}/{artworkId}/{chapterNumber}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    private LikeDto getUserLikesByArtworkIdAndChapterNumber(@PathVariable String userId,
                                                            @PathVariable String artworkId,
                                                            @PathVariable int chapterNumber) {

        LikeDto likeDto = likeService.getLikeBy(userId, artworkId, chapterNumber);
        return likeDto;
    }


    @PostMapping(path = "/{userId}/{artworkId}/{chapterNumber}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    private LikeRest postLike(
            @RequestBody LikeRequestModel requestModel,
            @PathVariable String userId,
            @PathVariable String artworkId,
            @PathVariable int chapterNumber
    ) {
        if (!ObjectUtils.allNotNull(
                requestModel,
                userId,
                artworkId,
                chapterNumber
        )) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        LikeDto likeDto = likeService.createLike(userId, artworkId, chapterNumber, requestModel.isLike());

        return LikeMapper.INSTANCE.dtoToRest(likeDto);
    }
}
