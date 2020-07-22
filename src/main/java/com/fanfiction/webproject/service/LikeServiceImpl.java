package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.LikeDto;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Like;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.exceptions.LikeServiceException;
import com.fanfiction.webproject.mappers.LikeMapper;
import com.fanfiction.webproject.repository.LikeRepository;
import com.fanfiction.webproject.service.interfaces.ChapterService;
import com.fanfiction.webproject.service.interfaces.LikeService;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {


    private final LikeRepository likeRepository;
    private final ChapterService chapterService;
    private final UserService userService;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, ChapterService chapterService, UserService userService) {
        this.likeRepository = likeRepository;
        this.chapterService = chapterService;
        this.userService = userService;
    }

    @Override
    public LikeDto getLikeBy(String userId, String chapterId) {
        Like like = likeRepository.findLikeByUserEntityUserIdAndChapterChapterId(userId, chapterId);
        return LikeMapper.INSTANCE.entityToDto(like);
    }

    @Override
    public LikeDto create(String userId, String chapterId, boolean likeValue) {
        Chapter chapter = chapterService.getByChapterId(chapterId);
        UserEntity userEntity = userService.getUserEntityByUserId(userId);
        if (checkLikeExist(userId, chapter.getChapterId())) {
            throw new LikeServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
        return createLike(likeValue, chapter, userEntity);
    }

    private LikeDto createLike(boolean like, Chapter chapter, UserEntity userEntity) {
        Like createdLike = new Like();
        createdLike.setChapter(chapter);
        createdLike.setUserEntity(userEntity);
        createdLike.setValue(like);
        Like saved = likeRepository.save(createdLike);
        return LikeMapper.INSTANCE.entityToDto(saved);
    }

    private boolean checkLikeExist(String userId, String chapterId) {
        return likeRepository.findLikeByUserEntityUserIdAndChapterChapterId(userId, chapterId) != null;
    }

}
