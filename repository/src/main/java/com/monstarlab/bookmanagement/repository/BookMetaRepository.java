package com.monstarlab.bookmanagement.repository;

import com.monstarlab.bookmanagement.entity.BookMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookMetaRepository extends JpaRepository<BookMetaEntity, Long>, JpaSpecificationExecutor<BookMetaEntity> {

    List<BookMetaEntity> findByIdIn(List<Long> ids);
}
