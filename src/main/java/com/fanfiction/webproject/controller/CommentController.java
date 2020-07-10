package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.entity.Comment;
import com.fanfiction.webproject.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CommentService commentService;


    @MessageMapping("/comments")
    @SendTo("/topic/comments")
    public List<Comment> availableGames(@Payload String artworkId) {
        return commentService.findCommentsByArtworkId(artworkId);
    }

    @MessageMapping("/postComment")
    public void joinGame(Principal principal, @Payload String comment) {
        System.out.println(comment);

    }


}
