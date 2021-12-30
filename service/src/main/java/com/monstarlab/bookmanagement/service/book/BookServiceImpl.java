package com.monstarlab.bookmanagement.service.book;

import com.monstarlab.bookmanagement.entity.BookEntity;
import com.monstarlab.bookmanagement.entity.BookMetaEntity;
import com.monstarlab.bookmanagement.entity.UserEntity;
import com.monstarlab.bookmanagement.exception.BadRequestException;
import com.monstarlab.bookmanagement.exception.RecordNotFoundException;
import com.monstarlab.bookmanagement.model.book.BookValidationDTO;
import com.monstarlab.bookmanagement.model.dto.email.EmailDto;
import com.monstarlab.bookmanagement.model.dto.user.UserDto;
import com.monstarlab.bookmanagement.model.request.book.CreateBookRequest;
import com.monstarlab.bookmanagement.model.request.book.IssueBookRequest;
import com.monstarlab.bookmanagement.model.request.book.UpdateBookRequest;
import com.monstarlab.bookmanagement.model.response.book.BookResponse;
import com.monstarlab.bookmanagement.service.BaseService;
import com.monstarlab.bookmanagement.service.BookEntityService;
import com.monstarlab.bookmanagement.service.BookMetaEntityService;
import com.monstarlab.bookmanagement.service.UserEntityService;
import com.monstarlab.bookmanagement.service.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl extends BaseService implements BookService {

    private final UserEntityService userEntityService;
    private final BookEntityService bookEntityService;
    private final BookMetaEntityService bookMetaEntityService;
    private final BookMapper bookMapper;
    private final UserMapper userMapper;

    @Override
    public BookResponse createBook(CreateBookRequest request) {
        BookEntity entity = bookMapper.mapToEntity(request);
        bookEntityService.save(entity);
        return bookMapper.mapToBookResponseDTO(entity);
    }

    @Override
    public BookResponse updateBook(UpdateBookRequest request) {
        BookEntity bookEntity = bookEntityService
                .findById(request.getId())
                .orElseThrow(() -> new RecordNotFoundException(
                        super.getLocaleMessage("api.response.NOT_FOUND.message")));

        bookMapper.fillUpdatableEntity(bookEntity, request);
        bookEntityService.save(bookEntity);
        return bookMapper.mapToBookResponseDTO(bookEntity);
    }

    @Override
    public UserDto issueBook(IssueBookRequest request){
        BookValidationDTO validationDTO = validateIssuedBookRequest(request);

        bookMapper.fillIssuableEntity(validationDTO.getBookEntities(), validationDTO.getMetaEntities(), validationDTO.getUserEntity());
        userEntityService.save(validationDTO.getUserEntity());
        bookMetaEntityService.save(validationDTO.getMetaEntities());

        sendEmail(EmailDto.builder()
                .email("test@gmail.com")
                .body("Mail sent for test")
                .build());
        return userMapper.mapToDTO(validationDTO.getUserEntity());
    }

    private BookValidationDTO validateIssuedBookRequest(IssueBookRequest request) {
        if(CollectionUtils.isEmpty(request.getBookIds()))
            throw new BadRequestException(
                    super.getLocaleMessage("validation.constraints.issueBook.Empty.message"));

        if(request.getBookIds().size() > props.getMaxIssueBook())
            throw new BadRequestException(
                    super.getLocaleMessage("validation.constraints.issueBook.Invalid.Max.message"));

        UserEntity userEntity = userEntityService
                .findById(request.getUserId())
                .orElseThrow(() -> new RecordNotFoundException(
                        super.getLocaleMessage("validation.constraints.userId.NotFound.message")));

        List<BookEntity> bookEntities = bookEntityService.findBooksByIdIn(request.getBookIds());
        if(CollectionUtils.isEmpty(bookEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        List<BookMetaEntity> bookMetaEntities = bookEntities.stream()
                .map(BookEntity::getBookMeta)
                .collect(Collectors.toList());

        long issueAbleBookListSize = bookMetaEntities.stream()
                .filter(x -> x.getNoOfCopy() > 0).count();

        if(issueAbleBookListSize != request.getBookIds().size())
            throw new BadRequestException(
                    super.getLocaleMessage("validation.constraints.book.not.exist.message"));

        return BookValidationDTO.builder()
                .userEntity(userEntity)
                .bookEntities(bookEntities)
                .metaEntities(bookMetaEntities)
                .build();
    }

    private BookValidationDTO validateSubmitBookRequest(IssueBookRequest request) {
        UserEntity userEntity = userEntityService
                .findById(request.getUserId())
                .orElseThrow(() -> new RecordNotFoundException(
                        super.getLocaleMessage("validation.constraints.userId.NotFound.message")));

        List<BookEntity> bookEntities = bookEntityService.findBooksByIdIn(request.getBookIds());
        if(CollectionUtils.isEmpty(bookEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        List<BookMetaEntity> bookMetaEntities = bookEntities.stream()
                .map(BookEntity::getBookMeta)
                .collect(Collectors.toList());

        return BookValidationDTO.builder()
                .userEntity(userEntity)
                .bookEntities(bookEntities)
                .metaEntities(bookMetaEntities)
                .build();
    }

    @Override
    public UserDto submitBook(IssueBookRequest request) {
        BookValidationDTO validationDTO = validateSubmitBookRequest(request);

        bookMapper.fillSubmittableEntity(validationDTO.getBookEntities(), validationDTO.getMetaEntities(), validationDTO.getUserEntity());
        userEntityService.save(validationDTO.getUserEntity());
        bookMetaEntityService.save(validationDTO.getMetaEntities());

        return userMapper.mapToDTO(validationDTO.getUserEntity());
    }

    @Override
    public List<BookResponse> findAllBooks() {
        List<BookEntity> books = bookEntityService.findAll();
        if(CollectionUtils.isEmpty(books))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        return bookMapper.mapToBookResponseDTO(books);
    }

    @Override
    public BookResponse findBookById(long id){
        return bookEntityService
                .findById(id)
                .map(bookMapper::mapToBookResponseDTO)
                .orElseThrow(()-> new RecordNotFoundException(
                        super.getLocaleMessage("validation.constraints.book.not.exist.message")));

    }

    @Override
    public BookResponse deleteBook(long id){
       return bookMapper.mapToBookResponseDTO(bookEntityService.delete(id));
    }

}
