package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.Comment;
import com.fanfiction.webproject.repository.CommentRepository;
import com.fanfiction.webproject.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findCommentsByArtworkId(String artworkId) {
        return commentRepository.findByArtworkArtworkId(artworkId);
    }
}
