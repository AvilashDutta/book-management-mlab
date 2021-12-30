package com.monstarlab.bookmanagement.service;

import com.monstarlab.bookmanagement.entity.BookMetaEntity;
import com.monstarlab.bookmanagement.repository.BookMetaRepository;
import org.springframework.stereotype.Service;

@Service
public class BookMetaEntityService extends BaseCRUDService<BookMetaEntity, BookMetaRepository> {
    public BookMetaEntityService(BookMetaRepository repository) {
        super(repository);
    }

}
