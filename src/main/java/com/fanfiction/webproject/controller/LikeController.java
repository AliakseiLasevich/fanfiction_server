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

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping(path = "/users/{userId}/chapters/{chapterId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public LikeRest getUserLikesByChapterIdAndChapterNumber(@PathVariable String userId,
                                                            @PathVariable String chapterId) {
        LikeDto likeDto = likeService.getLikeBy(userId, chapterId);
        return LikeMapper.INSTANCE.dtoToRest(likeDto);
    }


    @PostMapping(path = "/chapters/{chapterId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#requestModel.userId == principal.userId")
    public LikeRest postLike(@RequestBody LikeRequestModel requestModel,
                             @PathVariable String chapterId) {
        validateLikeRequestModel(requestModel, chapterId);
        LikeDto likeDto = likeService.create(requestModel.getUserId(), chapterId, requestModel.isLike());
        return LikeMapper.INSTANCE.dtoToRest(likeDto);
    }

    private void validateLikeRequestModel(LikeRequestModel requestModel, String chapterId) {
        if (!ObjectUtils.allNotNull(requestModel, chapterId)) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }
}