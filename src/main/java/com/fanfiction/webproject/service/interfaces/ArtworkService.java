package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.dto.ArtworkPreviewPageDto;
import com.fanfiction.webproject.entity.Artwork;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Comment;

import java.util.List;

public interface ArtworkService {
    List<ArtworkDto> findAll();

    ArtworkDto findById(String artworkId);

    List<ArtworkDto> findByUserId(String userId);

    ArtworkDto createArtwork(ArtworkDto artworkDto);

    ArtworkPreviewPageDto getArtworksPreviewPage(int page, int limit);

    ArtworkPreviewPageDto getArtworksPreviewPageByUserId(String userId, int page, int limit);

    Artwork findArtworkEntityByArtworkId(String artworkId);

    ArtworkDto findByComment(Comment comment);

    ArtworkDto findByChapter(Chapter chapter);
}
