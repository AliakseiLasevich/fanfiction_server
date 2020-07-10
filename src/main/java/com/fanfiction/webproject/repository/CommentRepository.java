package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArtworkArtworkId(String artworkId);
}
