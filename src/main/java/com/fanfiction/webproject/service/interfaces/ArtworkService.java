package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.dto.ArtworkPreviewPageDto;

import java.util.List;

public interface ArtworkService {
    List<ArtworkDto> findAll();

    ArtworkDto findById(String artworkId);

    List<ArtworkDto> findByUserId(String userId);

    ArtworkDto createArtwork(ArtworkDto artworkDto);

    ArtworkPreviewPageDto getArtworksPreviewPage(int page, int limit);

    ArtworkPreviewPageDto getArtworksPreviewPageByUserId(String userId, int page, int limit);
}
