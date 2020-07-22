package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findLikeByUserEntityUserIdAndChapterChapterId(String userId, String chapterId);


}
