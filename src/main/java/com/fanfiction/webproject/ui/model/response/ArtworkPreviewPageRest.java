package com.fanfiction.webproject.ui.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ArtworkPreviewPageRest {

    private List<ArtworkPreviewRest> artworksPreviews;
    private int pagesCount;

    public ArtworkPreviewPageRest(List<ArtworkPreviewRest> artworksPreviews, int pagesCount) {
        this.artworksPreviews = artworksPreviews;
        this.pagesCount = pagesCount;
    }
}
