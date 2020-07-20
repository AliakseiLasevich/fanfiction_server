package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Chapter findChapterByArtworkArtworkIdAndChapterNumber(String artworkId, int chapterNumber);

    List<Chapter> findChapterByArtworkArtworkId(String artworkId);

    Chapter findChapterByChapterId(String chapterId);
}
