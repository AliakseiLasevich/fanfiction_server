package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.LikeDto;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Like;

import java.util.List;

public interface LikeService {
    List<LikeDto> getLikesBy(String userId, String artworkId);

    LikeDto create(String userId, String artworkId, int chapterNumber, boolean like);

    LikeDto update(String userId, String artworkId, int chapterNumber, boolean like);

    List<Like> getLikesByChapter(Chapter chapter);
}
