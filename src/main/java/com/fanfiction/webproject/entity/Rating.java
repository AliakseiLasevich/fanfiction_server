package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import javax.persistence.*;

@Entity
@Data
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "value")
    @Min(0)
    @Max(5)
    private double value;

    @OneToOne
    private UserEntity userEntity;

    @OneToOne
    private Artwork artwork;
}
