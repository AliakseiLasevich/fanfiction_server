package com.fanfiction.webproject.dto;

import lombok.Data;

@Data
public class ChapterDto {
    private int chapterNumber;
    private String title;
    private String content;
    private String imageUrl;
    private boolean hasUserLike;
}
