package com.fanfiction.webproject.entity;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@Indexed
public class Chapter implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 10000)
    private int chapterNumber;

    @Column(nullable = false)
    private String title;

    @Field
    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String imageUrl;
}
