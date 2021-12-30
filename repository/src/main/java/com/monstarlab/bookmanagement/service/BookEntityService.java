package com.monstarlab.bookmanagement.service;


import com.monstarlab.bookmanagement.entity.BookEntity;
import com.monstarlab.bookmanagement.exception.RecordNotFoundException;
import com.monstarlab.bookmanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookEntityService extends BaseCRUDService<BookEntity, BookRepository> {
    public BookEntityService(BookRepository repository) {
        super(repository);
    }

    public List<BookEntity> findBooksByIdIn(Set<Long> ids){
        return repository.findByIdIn(ids);
    }

    @Override
    public BookEntity delete(long id){
        return repository.findById(id)
                .map(bookEntity -> {
                    bookEntity.getUsers().forEach(user -> user.removeBook(bookEntity));
                    bookEntity.getUsers().clear();
                    repository.delete(bookEntity);
                    return bookEntity;
                }).orElseThrow(() -> new RecordNotFoundException("api.response.NOT_FOUND.message"));
    }
}
