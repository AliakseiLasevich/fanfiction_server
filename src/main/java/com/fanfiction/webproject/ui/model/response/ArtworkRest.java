package com.fanfiction.webproject.ui.model.response;

import com.fanfiction.webproject.entity.Genre;
import com.fanfiction.webproject.entity.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArtworkRest {

    private String artworkId;
    private String name;
    private String summary;
    private List<ChapterRest> chapters;
    private List<Tag> tags;
    private Genre genre;
    private LocalDateTime creationDate;
    private String authorName;
    private String authorId;
}
