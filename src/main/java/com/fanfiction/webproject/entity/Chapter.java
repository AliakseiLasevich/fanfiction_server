package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Chapter implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private int chapterNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String imageUrl;
}
