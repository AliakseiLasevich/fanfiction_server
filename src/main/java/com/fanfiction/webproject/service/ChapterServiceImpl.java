package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.exceptions.ChapterServiceException;
import com.fanfiction.webproject.repository.ChapterRepository;
import com.fanfiction.webproject.service.interfaces.ChapterService;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public Chapter getByArtworkIdAndChapterNumber(String artworkId, int chapterNumber) {
        Chapter returnValue = chapterRepository.findChapterByArtworkArtworkIdAndChapterNumber(artworkId, chapterNumber);
        if (returnValue == null) {
            throw new ChapterServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return returnValue;
    }

    @Override
    public List<Chapter> getByArtworkId(String artworkId) {
        return chapterRepository.findChapterByArtworkArtworkId(artworkId);
    }
}
