package com.monstarlab.bookmanagement.service.role;

import com.monstarlab.bookmanagement.entity.RoleEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {

    @Transactional
    List<RoleEntity> createRoles(List<String> roles);
}
