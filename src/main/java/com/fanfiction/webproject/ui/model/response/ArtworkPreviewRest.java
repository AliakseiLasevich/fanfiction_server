package com.fanfiction.webproject.ui.model.response;

import com.fanfiction.webproject.entity.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArtworkPreviewRest {

    private String artworkId;
    private String authorName;
    private String authorId;
    private String name;
    private String summary;
    private LocalDateTime creationDate;
    private List<Tag> tags;
}
