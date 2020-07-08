package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Chapter implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 10000)
    private int chapterNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String imageUrl;
}
