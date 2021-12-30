package com.monstarlab.bookmanagement.entity;

import com.monstarlab.bookmanagement.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Data
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Collection<RoleEntity> roles = new HashSet<>();

    public void addRole(RoleEntity roleEntity) {
        roles.add(roleEntity);
        roleEntity.getUsers().add(this);
    }

    public void removeRole(RoleEntity roleEntity) {
        roles.remove(roleEntity);
        roleEntity.getUsers().remove(this);
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_book", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private Collection<BookEntity> books = new HashSet<>();

    public void addBook(BookEntity bookEntity) {
        books.add(bookEntity);
        bookEntity.getUsers().add(this);
    }

    public void removeBook(BookEntity bookEntity) {
        books.remove(bookEntity);
        bookEntity.getUsers().remove(this);
    }

    @Builder
    public UserEntity(String username, String password, String fullName,
                      String email, Collection<RoleEntity> roles) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.roles.addAll(roles);
    }
}
