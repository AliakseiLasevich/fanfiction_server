package com.fanfiction.webproject.ui.model.response;

import lombok.Data;

@Data
public class ArtworkPreviewRest {

    private String artworkId;
    private String authorName;
    private String authorId;
    private String name;
    private String summary;
}
