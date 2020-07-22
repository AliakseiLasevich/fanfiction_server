package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Chapter;

import java.util.List;

public interface ChapterService {

    List<Chapter> updateChapters(String artworkId, List<Chapter> chapters);

    Chapter getByChapterId(String chapterId);
}
