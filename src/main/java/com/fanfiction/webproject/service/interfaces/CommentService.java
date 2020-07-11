package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.Comment;
import com.fanfiction.webproject.ui.model.request.CommentRequest;

import java.util.List;

public interface CommentService {
    List<Comment> findCommentsByArtworkId(String artworkId);

    Comment save(String artworkId, CommentRequest commentRequest);
}
