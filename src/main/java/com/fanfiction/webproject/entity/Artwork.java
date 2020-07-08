package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Artwork implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String artworkId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 255)
    private String summary;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDate  creationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Chapter> chapters;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Tag> tags;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
}
