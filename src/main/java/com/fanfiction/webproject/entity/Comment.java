package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "artworkId")
    private Artwork artwork;

    @ManyToMany(mappedBy = "comments")
    private List<UserEntity> userEntities;
}
