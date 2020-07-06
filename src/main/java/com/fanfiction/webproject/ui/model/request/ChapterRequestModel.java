package com.fanfiction.webproject.ui.model.request;

import lombok.Data;

@Data
public class ChapterRequestModel {

    private int index;
    private String title;
    private String content;
    private String imgUrl;
}
