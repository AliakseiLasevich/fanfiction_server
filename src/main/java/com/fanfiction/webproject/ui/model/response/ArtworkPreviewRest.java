package com.fanfiction.webproject.ui.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArtworkPreviewRest {

    private String artworkId;
    private String authorName;
    private String authorId;
    private String name;
    private String summary;
    private LocalDateTime creationDate;
}
