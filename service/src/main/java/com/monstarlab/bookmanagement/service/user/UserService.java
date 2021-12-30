package com.monstarlab.bookmanagement.service.user;

import com.monstarlab.bookmanagement.model.dto.user.UserDto;
import com.monstarlab.bookmanagement.model.request.user.UserCreateRequest;
import com.monstarlab.bookmanagement.model.request.user.UserUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    @Transactional
    UserDto createUser(UserCreateRequest userCreateRequest);

    @Transactional
    UserDto updateUser(UserUpdateRequest userUpdateRequest);

    @Transactional
    UserDto deleteUser(long id);

    @Transactional(readOnly = true)
    List<UserDto> getAllUser();

    @Transactional(readOnly = true)
    UserDto getUserById(long id);
}
