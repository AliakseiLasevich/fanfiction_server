package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@Table(name = "authorities")
public class AuthorityEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 25)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity> roles;
}
