package com.monstarlab.bookmanagement.entity;

import com.monstarlab.bookmanagement.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users = new HashSet<>();

    @Builder
    public RoleEntity(String name) {
        this.name = name;
    }
}
