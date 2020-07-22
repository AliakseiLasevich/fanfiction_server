package com.fanfiction.webproject.ui.model.request;

import lombok.Data;

@Data
public class LikeRequestModel {

    private String userId;
    private boolean like;
}
