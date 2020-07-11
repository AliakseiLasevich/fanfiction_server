package com.fanfiction.webproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private LocalDateTime publicationDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "artworkId")
    private Artwork artwork;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
