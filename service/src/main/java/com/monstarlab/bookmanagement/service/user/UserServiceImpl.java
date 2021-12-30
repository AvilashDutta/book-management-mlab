package com.monstarlab.bookmanagement.service.user;

import com.monstarlab.bookmanagement.entity.RoleEntity;
import com.monstarlab.bookmanagement.entity.UserEntity;
import com.monstarlab.bookmanagement.exception.NotUniqueException;
import com.monstarlab.bookmanagement.exception.RecordNotFoundException;
import com.monstarlab.bookmanagement.model.dto.user.UserDto;
import com.monstarlab.bookmanagement.model.request.user.UserCreateRequest;
import com.monstarlab.bookmanagement.model.request.user.UserUpdateRequest;
import com.monstarlab.bookmanagement.service.BaseService;
import com.monstarlab.bookmanagement.service.RoleEntityService;
import com.monstarlab.bookmanagement.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {

    private final UserEntityService userEntityService;
    private final RoleEntityService roleEntityService;
    private final UserMapper mapper;

    @Override
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        List<RoleEntity> roleEntityList = roleEntityService.findRolesIn(userCreateRequest.getRoles());

        userEntityService.findUserByUsername(userCreateRequest.getUsername())
                .ifPresent(u -> {
                    throw new NotUniqueException(
                            messageHelper.getLocalMessage("validation.constraints.username.exists.message"));
                });

        UserEntity user = mapper.mapToNewUserEntity(userCreateRequest, roleEntityList);
        userEntityService.save(user);
        return mapper.mapToDTO(user);
    }

    @Override
    public UserDto updateUser(UserUpdateRequest userUpdateRequest) {
        UserEntity user = userEntityService
                .findById(userUpdateRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(
                        super.getLocaleMessage("validation.constraints.userId.NotFound.message")));

    mapper.fillUpdatableEntity(user, userUpdateRequest);
    userEntityService.save(user);
    return mapper.mapToDTO(user);
    }

    @Override
    public UserDto deleteUser(long id) {
        return mapper.mapToDTO(userEntityService.delete(id));
    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserEntity> userEntityList = userEntityService.findAll();
        return mapper.mapToDTO(userEntityList);
    }

    @Override
    public UserDto getUserById(long id) {
        UserEntity user = userEntityService
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        super.getLocaleMessage("validation.constraints.userId.NotFound.message")));

        return mapper.mapToDTO(user);
    }
}
