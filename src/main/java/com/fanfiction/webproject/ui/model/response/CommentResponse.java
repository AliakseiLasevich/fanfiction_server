package com.fanfiction.webproject.ui.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {

    private String id;
    private String content;
    private String userId;
    private String userNickName;
    private LocalDateTime publicationDate;

}
