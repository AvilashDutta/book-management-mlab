package com.monstarlab.bookmanagement.service.book;

import com.monstarlab.bookmanagement.model.dto.user.UserDto;
import com.monstarlab.bookmanagement.model.request.book.CreateBookRequest;
import com.monstarlab.bookmanagement.model.request.book.IssueBookRequest;
import com.monstarlab.bookmanagement.model.request.book.UpdateBookRequest;
import com.monstarlab.bookmanagement.model.response.book.BookResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {

    @Transactional
    BookResponse createBook(CreateBookRequest request);

    @Transactional
    BookResponse updateBook(UpdateBookRequest request);

    @Transactional
    UserDto issueBook(IssueBookRequest request);

    @Transactional
    UserDto submitBook(IssueBookRequest request);

    @Transactional(readOnly = true)
    List<BookResponse> findAllBooks();

    @Transactional(readOnly = true)
    BookResponse findBookById(long id);

    BookResponse deleteBook(long id);
}
