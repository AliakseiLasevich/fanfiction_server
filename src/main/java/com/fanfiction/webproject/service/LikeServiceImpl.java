package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.LikeDto;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Like;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.mappers.LikeMapper;
import com.fanfiction.webproject.repository.LikeRepository;
import com.fanfiction.webproject.service.interfaces.ChapterService;
import com.fanfiction.webproject.service.interfaces.LikeService;
import com.fanfiction.webproject.service.interfaces.UserService;
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
    public LikeDto getLikeBy(String userId, String artworkId, int chapterNumber) {
        Chapter chapter = chapterService.getByArtworkIdAndChapterNumber(artworkId, chapterNumber - 1);
        Like l = likeRepository.findLikeByUserEntityUserIdAndChapter(userId, chapter);
        return LikeMapper.INSTANCE.entityToDto(l);
    }

    @Override
    public LikeDto createLike(String userId, String artworkId, int chapterNumber, boolean like) {
        Chapter chapter = chapterService.getByArtworkIdAndChapterNumber(artworkId, chapterNumber - 1);
        UserEntity userEntity = userService.getUserEntityByUserId(userId);
        Like createdLike = new Like();
        createdLike.setChapter(chapter);
        createdLike.setUserEntity(userEntity);
        Like saved = likeRepository.save(createdLike);
        return LikeMapper.INSTANCE.entityToDto(saved);
    }
}
