package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Chapter;

import java.util.List;

public interface ChapterService {
    Chapter getByArtworkIdAndChapterNumber(String artworkId, int chapterNumber);

    List<Chapter> getByArtworkId(String artworkId);
}
