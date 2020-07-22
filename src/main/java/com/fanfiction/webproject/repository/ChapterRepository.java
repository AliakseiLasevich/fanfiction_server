package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    Chapter findChapterByChapterId(String chapterId);
}
