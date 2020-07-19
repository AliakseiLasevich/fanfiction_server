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

import java.util.List;
import java.util.stream.Collectors;

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
    public List<LikeDto> getLikesBy(String userId, String artworkId) {
        List<Like> likes = getUserLikesByArtworkId(userId, artworkId);
        return likes.stream()
                .map(LikeMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Like> getLikesByChapter(Chapter chapter) {
        return likeRepository.findLikesByChapter(chapter);
    }

    @Override
    public LikeDto create(String userId, String artworkId, int chapterNumber, boolean likeValue) {
        Chapter chapter = chapterService.getByArtworkIdAndChapterNumber(artworkId, chapterNumber);
        UserEntity userEntity = userService.getUserEntityByUserId(userId);
        if (checkLikeExist(userId, chapter)) {
            throw new LikeServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
        return createLike(likeValue, chapter, userEntity);
    }

    @Override
    public LikeDto update(String userId, String artworkId, int chapterNumber, boolean like) {
        Chapter chapter = chapterService.getByArtworkIdAndChapterNumber(artworkId, chapterNumber - 1);
        if (!checkLikeExist(userId, chapter)) {
            throw new LikeServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return updateLike(userId, artworkId, chapterNumber, like);
    }

    private List<Like> getUserLikesByArtworkId(String userId, String artworkId) {
        List<Chapter> chapters = chapterService.getChaptersByArtworkId(artworkId);
        List<Like> likes = chapters.stream()
                .map(chapter -> likeRepository.findLikeByUserEntityUserIdAndChapter(userId, chapter))
                .collect(Collectors.toList());
        return likes;
    }

    private LikeDto createLike(boolean like, Chapter chapter, UserEntity userEntity) {
        Like createdLike = new Like();
        createdLike.setChapter(chapter);
        createdLike.setUserEntity(userEntity);
        createdLike.setValue(like);
        Like saved = likeRepository.save(createdLike);
        return LikeMapper.INSTANCE.entityToDto(saved);
    }

    private LikeDto updateLike(String userId, String artworkId, int chapterNumber, boolean likeValue) {
//        Like likeToUpdate = findLikeByChapterAndUserId(userId, artworkId, chapterNumber);
        Like likeToUpdate = null;
        likeToUpdate.setValue(likeValue);
        Like saved = likeRepository.save(likeToUpdate);
        return LikeMapper.INSTANCE.entityToDto(saved);
    }

    private boolean checkLikeExist(String userId, Chapter chapter) {
        return likeRepository.findLikeByUserEntityUserIdAndChapter(userId, chapter) != null;
    }

}
