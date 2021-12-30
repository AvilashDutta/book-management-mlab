package com.monstarlab.bookmanagement.controller;

import com.monstarlab.bookmanagement.model.dto.user.UserDto;
import com.monstarlab.bookmanagement.model.request.book.CreateBookRequest;
import com.monstarlab.bookmanagement.model.request.book.IssueBookRequest;
import com.monstarlab.bookmanagement.model.request.book.UpdateBookRequest;
import com.monstarlab.bookmanagement.model.request.user.UserCreateRequest;
import com.monstarlab.bookmanagement.model.request.user.UserUpdateRequest;
import com.monstarlab.bookmanagement.model.response.ApiResponse;
import com.monstarlab.bookmanagement.model.response.book.BookResponse;
import com.monstarlab.bookmanagement.service.book.BookService;
import com.monstarlab.bookmanagement.service.user.UserService;
import com.monstarlab.bookmanagement.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController extends BaseController {

    private final BookService bookService;

    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@RequestBody @Valid CreateBookRequest request,
                                                                BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(bookService.createBook(request)));
    }

    @PutMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@RequestBody @Valid UpdateBookRequest request,
                                                                BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(bookService.updateBook(request)));
    }

    @GetMapping("/books")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks(){
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(bookService.findAllBooks()));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable long id){
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(bookService.findBookById(id)));
    }

    @PostMapping("/books/issue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> issueBook(@RequestBody @Valid IssueBookRequest request,
                                                          BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(bookService.issueBook(request)));
    }

    @PostMapping("/books/submit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> submitBook(@RequestBody @Valid IssueBookRequest request,
                                                          BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(bookService.submitBook(request)));
    }

}
