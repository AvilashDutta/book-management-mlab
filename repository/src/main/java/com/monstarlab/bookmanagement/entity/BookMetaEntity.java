package com.monstarlab.bookmanagement.entity;

import com.monstarlab.bookmanagement.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_meta")
@Data
@NoArgsConstructor
public class BookMetaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "no_of_copy", nullable = false)
    private int noOfCopy;

    @Column(name = "release_date", nullable = false)
    private String releaseDate;

    @Builder
    public BookMetaEntity(BookEntity book,
                          String authorName,
                          String description,
                          String name,
                          int noOfCopy,
                          String releaseDate) {
        this.book = book;
        this.authorName = authorName;
        this.description = description;
        this.name = name;
        this.noOfCopy = noOfCopy;
        this.releaseDate = releaseDate;
    }
}
