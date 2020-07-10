package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findCommentsByArtworkId(String artworkId);
}
