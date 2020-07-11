package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.entity.Comment;
import com.fanfiction.webproject.mappers.CommentMapper;
import com.fanfiction.webproject.service.interfaces.CommentService;
import com.fanfiction.webproject.ui.model.request.CommentRequest;
import com.fanfiction.webproject.ui.model.response.CommentResponse;
import com.fanfiction.webproject.ui.model.response.OperationStatusModel;
import com.fanfiction.webproject.ui.model.response.RequestOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CommentService commentService;


    @MessageMapping("/comments/{artworkId}")
    @SendTo("/topic/comments/{artworkId}")
    public List<CommentResponse> findComments(@DestinationVariable String artworkId) {
        return commentService.findCommentsByArtworkId(artworkId).stream()
                .map(CommentMapper.INSTANCE::entityToResponse)
                .collect(Collectors.toList());
    }

    @MessageMapping("/postComment/{artworkId}")
    @SendTo("/topic/comments/{artworkId}/newComment")
    public CommentResponse saveComment(@DestinationVariable String artworkId,
                                       @Payload CommentRequest comment) {

        Comment savedComment = commentService.save(artworkId, comment);
        return CommentMapper.INSTANCE.entityToResponse(savedComment);
    }


}

