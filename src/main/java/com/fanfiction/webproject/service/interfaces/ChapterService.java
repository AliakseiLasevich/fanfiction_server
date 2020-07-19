package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Chapter;

import java.util.List;

public interface ChapterService {
    Chapter getByArtworkIdAndChapterNumber(String artworkId, int chapterNumber);

    List<Chapter> getChaptersByArtworkId(String artworkId);

    List<Chapter> updateChapters(String artworkId, List<Chapter> chapters);
}
