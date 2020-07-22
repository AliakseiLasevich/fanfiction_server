package com.fanfiction.webproject.ui.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ArtworkRequestModel {

    private String userId;
    private String name;
    private String summary;
    private String genre;
    private List<ChapterRequestModel> chapters;
    private List<String> tags;

}
