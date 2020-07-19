package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Like;
import com.fanfiction.webproject.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findLikeByUserEntityUserIdAndChapter(String userId, Chapter chapter);

    List<Like> findLikesByChapter(Chapter chapter);

}
