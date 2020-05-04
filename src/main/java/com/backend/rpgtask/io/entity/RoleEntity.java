package com.backend.rpgtask.io.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1355334493438470422L;

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
