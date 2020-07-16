package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.LikeDto;

public interface LikeService {
    LikeDto getLikeBy(String userId, String artworkId, int chapterNumber);

    LikeDto createLike(String userId, String artworkId, int chapterNumber, boolean like);
}
