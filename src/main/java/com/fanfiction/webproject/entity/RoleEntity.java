package com.fanfiction.webproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@Table(name = "roles")
public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 25)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    private Collection<AuthorityEntity> authorities;
}
