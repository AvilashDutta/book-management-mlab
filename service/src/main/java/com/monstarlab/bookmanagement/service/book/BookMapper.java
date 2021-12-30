package com.monstarlab.bookmanagement.service.book;

import com.monstarlab.bookmanagement.entity.BookEntity;
import com.monstarlab.bookmanagement.entity.BookMetaEntity;
import com.monstarlab.bookmanagement.entity.UserEntity;
import com.monstarlab.bookmanagement.model.request.book.CreateBookRequest;
import com.monstarlab.bookmanagement.model.request.book.UpdateBookRequest;
import com.monstarlab.bookmanagement.model.response.book.BookResponse;
import com.monstarlab.bookmanagement.model.response.user.IssuedUser;
import com.monstarlab.bookmanagement.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.monstarlab.bookmanagement.util.DateTimeUtils.toAPIDateFormat;
import static com.monstarlab.bookmanagement.util.DateTimeUtils.toDBDateFormat;

@Component
@Slf4j
public class BookMapper {

    public void fillIssuableEntity(List<BookEntity> bookEntities, List<BookMetaEntity> metaEntityList, UserEntity user){
        bookEntities.forEach(user::addBook);
        metaEntityList.forEach(bookMeta -> bookMeta.setNoOfCopy(bookMeta.getNoOfCopy() - 1));
    }

    public void fillSubmittableEntity(List<BookEntity> bookEntities, List<BookMetaEntity> metaEntityList, UserEntity user){
        bookEntities.forEach(user::removeBook);
        metaEntityList.forEach(bookMeta -> bookMeta.setNoOfCopy(bookMeta.getNoOfCopy() + 1));
    }

    public BookEntity mapToEntity(CreateBookRequest dto){
        BookEntity book = new BookEntity();
        book.setBookMeta(getBookMeta(dto));
        book.getBookMeta().setBook(book);
        return book;

    }

    private BookMetaEntity getBookMeta(CreateBookRequest dto) {
        return BookMetaEntity.builder()
                .authorName(dto.getAuthorName())
                .description(dto.getDescription())
                .noOfCopy(dto.getNoOfCopy())
                .name(dto.getName())
                .releaseDate(toDBDateFormat(dto.getReleaseDate()))
                .build();
    }

    public List<BookResponse> mapToBookResponseDTO(List<BookEntity> bookEntities){
        return bookEntities.stream()
                .map(this::mapToBookResponseDTO)
                .collect(Collectors.toList());
    }


    public BookResponse mapToBookResponseDTO(BookEntity bookEntity){
        return BookResponse
                .builder()
                .id(bookEntity.getId())
                .name(bookEntity.getBookMeta().getName())
                .description(bookEntity.getBookMeta().getDescription())
                .authorName(bookEntity.getBookMeta().getAuthorName())
                .noOfCopy(bookEntity.getBookMeta().getNoOfCopy())
                .releaseDate(toAPIDateFormat(bookEntity.getBookMeta().getReleaseDate()))
                .issuedUsers(mapToIssuedUserDTO(bookEntity.getUsers()))
                .build();
    }

    private List<IssuedUser> mapToIssuedUserDTO(List<UserEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToIssuedUserDTO)
                .collect(Collectors.toList());
    }

    private IssuedUser mapToIssuedUserDTO(UserEntity entity){
        return IssuedUser
                .builder()
                .name(entity.getFullName())
                .id(entity.getId())
                .build();
    }

    public void fillUpdatableEntity(BookEntity entity, UpdateBookRequest dto){
        entity.getBookMeta().setAuthorName(dto.getAuthorName());
        entity.getBookMeta().setDescription(dto.getDescription());
        entity.getBookMeta().setNoOfCopy(dto.getNoOfCopy());
        entity.getBookMeta().setReleaseDate(
                DateTimeUtils.toDBDateFormat(dto.getReleaseDate()));
    }
}
