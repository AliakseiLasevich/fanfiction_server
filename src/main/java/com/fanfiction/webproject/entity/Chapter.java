package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Chapter {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private long chapterId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String imageUrl;
}
