package com.monstarlab.bookmanagement.repository;

import com.monstarlab.bookmanagement.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findByNameIn(List<String> nameList);
}
