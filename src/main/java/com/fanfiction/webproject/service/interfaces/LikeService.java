package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.LikeDto;

public interface LikeService {

    LikeDto getLikeBy(String userId, String chapterId);

    LikeDto create(String userId, String chapterId, boolean like);

}
