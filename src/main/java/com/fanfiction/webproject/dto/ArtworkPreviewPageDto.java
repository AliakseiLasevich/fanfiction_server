package com.fanfiction.webproject.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArtworkPreviewPageDto {

    private List<ArtworkDto> artworkDtoList;
    private int pagesCount;

    public ArtworkPreviewPageDto(List<ArtworkDto> artworkDtoList, int pagesCount) {
        this.artworkDtoList = artworkDtoList;
        this.pagesCount = pagesCount;
    }
}
