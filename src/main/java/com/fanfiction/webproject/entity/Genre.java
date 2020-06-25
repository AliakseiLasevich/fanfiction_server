package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Genre {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;
}
