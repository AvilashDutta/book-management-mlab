package com.monstarlab.bookmanagement.model.book;

import com.monstarlab.bookmanagement.entity.BookEntity;
import com.monstarlab.bookmanagement.entity.BookMetaEntity;
import com.monstarlab.bookmanagement.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class BookValidationDTO {
    private UserEntity userEntity;
    @Singular
    private List<BookEntity> bookEntities;
    @Singular
    private List<BookMetaEntity> metaEntities;
}
