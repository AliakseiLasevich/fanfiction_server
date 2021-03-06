package com.fanfiction.webproject.dto;

import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Genre;
import com.fanfiction.webproject.entity.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArtworkDto {

    private String userId;
    private String artworkId;
    private String authorName;
    private String authorId;
    private String name;
    private String summary;
    private Genre genre;
    private List<Chapter> chapters;
    private List<Tag> tags;
    private LocalDateTime creationDate;

}
