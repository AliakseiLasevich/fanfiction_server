package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Chapter;

public interface ChapterService {
    Chapter getByArtworkIdAndChapterNumber(String artworkId, int chapterNumber);
}
