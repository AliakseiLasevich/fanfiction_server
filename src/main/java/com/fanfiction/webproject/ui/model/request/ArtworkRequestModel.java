package com.fanfiction.webproject.ui.model.request;

import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Genre;
import com.fanfiction.webproject.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArtworkRequestModel {

    private String summary;
    private List<Chapter> chapters;
    private List<Tag> tags;
    private Genre genre;
}
