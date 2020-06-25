package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.ArtworkDto;

import java.util.List;

public interface ArtworkService {
    List<ArtworkDto> findAll();

    ArtworkDto findById(String artworkId);

    List<ArtworkDto> findByUserId(String userId);

    ArtworkDto createArtwork(ArtworkDto artworkDto);
}
