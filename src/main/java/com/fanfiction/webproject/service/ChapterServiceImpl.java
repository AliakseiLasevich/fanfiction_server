package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.exceptions.ChapterServiceException;
import com.fanfiction.webproject.repository.ChapterRepository;
import com.fanfiction.webproject.service.interfaces.ChapterService;
import com.fanfiction.webproject.service.interfaces.LikeService;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private LikeService likeService;

    @Override
    public Chapter getByArtworkIdAndChapterNumber(String artworkId, int chapterNumber) {
        Chapter returnValue = chapterRepository.findChapterByArtworkArtworkIdAndChapterNumber(artworkId, chapterNumber);
        if (returnValue == null) {
            throw new ChapterServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return returnValue;
    }

    public List<Chapter> getChaptersByArtworkId(String artworkId) {
        return chapterRepository.findChapterByArtworkArtworkId(artworkId);
    }

    //    TODO handle if chapters size was increased
    @Override
    public List<Chapter> updateChapters(String artworkId, List<Chapter> newChapters) {
        List<Chapter> returnValue = new ArrayList<>();
        List<Chapter> oldChapters = getChaptersByArtworkId(artworkId);
        for (int i = 0; i < newChapters.size(); i++) {
            Chapter toEdit = newChapters.get(i);
            toEdit.setId(oldChapters.get(i).getId());
            toEdit.setLikes(likeService.getLikesByChapter(oldChapters.get(i)));
            returnValue.add(toEdit);
        }
        return returnValue;
    }

}
