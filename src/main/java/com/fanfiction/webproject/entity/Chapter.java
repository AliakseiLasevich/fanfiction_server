package com.fanfiction.webproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Indexed
public class Chapter implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String chapterId;

    @Column(nullable = false)
    private int chapterNumber;

    @Column(nullable = false)
    private String title;

    @Field
    @Column(nullable = false, length = 15000)
    private String content;

    @Column(nullable = true, length = 300)
    private String imageUrl;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @JsonIgnore
    @OneToMany(mappedBy = "chapter")
    private List<Like> likes;
}
