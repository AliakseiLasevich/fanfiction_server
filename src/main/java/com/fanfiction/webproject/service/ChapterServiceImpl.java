package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.exceptions.ChapterServiceException;
import com.fanfiction.webproject.repository.ChapterRepository;
import com.fanfiction.webproject.service.interfaces.ChapterService;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.fanfiction.webproject.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final Utils utils;

    public ChapterServiceImpl(ChapterRepository chapterRepository, Utils utils) {
        this.chapterRepository = chapterRepository;
        this.utils = utils;
    }

    @Override
    public Chapter getByChapterId(String chapterId) {
        Chapter returnValue = chapterRepository.findChapterByChapterId(chapterId);
        if (returnValue == null) {
            throw new ChapterServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return returnValue;
    }

    @Override
    public List<Chapter> updateChapters(String artworkId, List<Chapter> updatedChapters) {
        List<Chapter> returnValue = new ArrayList<>();
        for (Chapter chapter : updatedChapters) {
            if (chapter.getChapterId() != null) {
                Chapter ch = chapterRepository.findChapterByChapterId(chapter.getChapterId());
                ch.setChapterNumber(chapter.getChapterNumber());
                ch.setContent(chapter.getContent());
                ch.setImageUrl(chapter.getImageUrl());
                ch.setTitle(chapter.getTitle());
                returnValue.add(ch);
            } else {
                returnValue.add(createNewChapter(chapter));
            }
        }
        return returnValue;
    }

    private Chapter createNewChapter(Chapter chapter) {
        chapter.setChapterId(utils.generateRandomString(30));
        return chapterRepository.save(chapter);
    }

}
