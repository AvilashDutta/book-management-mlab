package com.monstarlab.bookmanagement.service.user;

import com.monstarlab.bookmanagement.entity.BookEntity;
import com.monstarlab.bookmanagement.entity.BookMetaEntity;
import com.monstarlab.bookmanagement.entity.RoleEntity;
import com.monstarlab.bookmanagement.entity.UserEntity;
import com.monstarlab.bookmanagement.enums.UserRoleEnum;
import com.monstarlab.bookmanagement.model.auth.CurrentUser;
import com.monstarlab.bookmanagement.model.dto.user.IssuedBook;
import com.monstarlab.bookmanagement.model.dto.user.UserDto;
import com.monstarlab.bookmanagement.model.request.user.UserCreateRequest;
import com.monstarlab.bookmanagement.model.request.user.UserUpdateRequest;
import com.monstarlab.bookmanagement.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.monstarlab.bookmanagement.util.DateTimeUtils.toAPIDateFormat;

@Component
@RequiredArgsConstructor
public class UserMapper extends BaseService {

    private final PasswordEncoder passwordEncoder;

    /** ENTITY MAPPING */
    public UserEntity mapToNewUserEntity(UserCreateRequest dto, Collection<RoleEntity> roleEntities){
        return UserEntity.builder()
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .roles(roleEntities)
                .build();
    }

    public void fillUpdatableEntity(UserEntity entity, UserUpdateRequest dto){
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
    }

    public List<UserDto> mapToDTO(List<UserEntity> entityList) {
        return entityList
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /** DTO MAPPING */
    public UserDto mapToDTO(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getFullName())
                .userName(entity.getUsername())
                .email(entity.getEmail())
                .roles(getRoleNames(entity.getRoles()))
                .issuedBooks(mapToIssueBooks((List<BookEntity>) entity.getBooks()))
                .build();
    }

    /** HELPERS */
    private List<String> getRoleNames(Collection<RoleEntity> roleEntities){
        return roleEntities
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());
    }

    /** Spring Security Model Mapping */
    public static User mapToUserDetails(UserEntity entity) {
        return new CurrentUser(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                getGrantedAuthorities(entity.getRoles())
        );
    }

    private static Collection<? extends GrantedAuthority> getGrantedAuthorities(Collection<RoleEntity> roles) {
            return roles.stream()
                    .map(roleEntity -> new SimpleGrantedAuthority(UserRoleEnum.getValueByName(roleEntity.getName())))
                    .collect(Collectors.toList());

    }

    private IssuedBook mapToIssueBook(BookMetaEntity metaEntity){
        return IssuedBook.builder()
                .id(metaEntity.getBook().getId())
                .name(metaEntity.getName())
                .description(metaEntity.getDescription())
                .authorName(metaEntity.getAuthorName())
                .noOfCopy(metaEntity.getNoOfCopy())
                .releaseDate(toAPIDateFormat(metaEntity.getReleaseDate()))
                .build();
    }

    private List<IssuedBook> mapToIssueBooks(List<BookEntity> bookEntities){
        return bookEntities
                .stream()
                .map(e ->
                        mapToIssueBook(e.getBookMeta()))
                .collect(Collectors.toList());
    }
}
