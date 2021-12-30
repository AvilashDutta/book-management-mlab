package com.monstarlab.bookmanagement.service;

import com.monstarlab.bookmanagement.entity.UserEntity;
import com.monstarlab.bookmanagement.exception.RecordNotFoundException;
import com.monstarlab.bookmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityService extends BaseCRUDService<UserEntity, UserRepository> {
    public UserEntityService(UserRepository repository) {
        super(repository);
    }

    public Optional<UserEntity> findUserByUsername(String userName){
        return repository.findDistinctByUsername(userName);
    }

    @Override
    public UserEntity delete(long id){
        return repository.findById(id)
                .map(userEntity -> {
                    userEntity.getRoles().forEach(role -> role.getUsers().remove(userEntity));
                    userEntity.getRoles().clear();
                    userEntity.getBooks().forEach( book -> book.setUsers(null));
                    userEntity.getBooks().clear();
                    repository.delete(userEntity);
                    return userEntity;
                }).orElseThrow(() -> new RecordNotFoundException("validation.constraints.userId.NotFound.message"));
    }
}
