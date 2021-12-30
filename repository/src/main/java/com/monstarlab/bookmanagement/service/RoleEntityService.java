package com.monstarlab.bookmanagement.service;

import com.monstarlab.bookmanagement.entity.RoleEntity;
import com.monstarlab.bookmanagement.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleEntityService extends BaseCRUDService<RoleEntity, RoleRepository> {

    public RoleEntityService(RoleRepository repository) {
        super(repository);
    }

    public List<RoleEntity> findRolesIn(List<String> nameList){
        return repository.findByNameIn(nameList);
    }
}
