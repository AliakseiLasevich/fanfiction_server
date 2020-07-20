package com.fanfiction.webproject.ui.model.response;

import lombok.Data;

@Data
public class ChapterRest {

    private String chapterId;
    private int chapterNumber;
    private String title;
    private String content;
    private String imageUrl;
}
