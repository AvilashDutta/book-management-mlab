package com.monstarlab.bookmanagement.repository;

import com.monstarlab.bookmanagement.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {

    List<BookEntity> findByIdIn(Set<Long> ids);
}
