package com.monstarlab.bookmanagement.entity;

import com.monstarlab.bookmanagement.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class BookEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "book",
            cascade = CascadeType.ALL)
    private BookMetaEntity bookMeta;

    @ManyToMany(mappedBy = "books", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();

    public void addUser(UserEntity userEntity){
        users.add(userEntity);
    }

    @Builder
    public BookEntity(BookMetaEntity bookMeta) {
        this.bookMeta = bookMeta;
    }
}
