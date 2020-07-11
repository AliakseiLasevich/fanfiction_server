package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.Comment;
import com.fanfiction.webproject.exceptions.CommentServiceException;
import com.fanfiction.webproject.repository.CommentRepository;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.service.interfaces.CommentService;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.request.CommentRequest;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArtworkService artworkService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, ArtworkService artworkService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.artworkService = artworkService;
    }

    @Override
    public List<Comment> findCommentsByArtworkId(String artworkId) {
        List<Comment> comments = commentRepository.findByArtworkArtworkId(artworkId);
        return comments;
    }

    @Override
    public Comment save(String artworkId, CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setArtwork(artworkService.findArtworkEntityByArtworkId(artworkId));
        comment.setUserEntity(userService.getUserEntityByUserId(commentRequest.getUserId()));
        comment.setPublicationDate(LocalDateTime.now());
        comment.setContent(commentRequest.getComment());

        if (ObjectUtils.allNotNull(
                comment.getArtwork(),
                comment.getContent(),
                comment.getPublicationDate(),
                comment.getUserEntity()
        )) {
            commentRepository.save(comment);
        } else throw new CommentServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        return comment;
    }
}
