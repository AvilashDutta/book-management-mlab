package com.monstarlab.bookmanagement.service.role;

import com.monstarlab.bookmanagement.entity.RoleEntity;
import com.monstarlab.bookmanagement.service.BaseService;
import com.monstarlab.bookmanagement.service.RoleEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseService implements RoleService {

    private final RoleEntityService roleEntityService;

    @Override
    public List<RoleEntity> createRoles(List<String> roles) {
        Function<String, RoleEntity> mapFunction = r -> RoleEntity.builder().name(r).build();

        List<RoleEntity> roleEntities = roles.stream()
                .map(mapFunction)
                .collect(Collectors.toList());
        return roleEntityService.save(roleEntities);
    }
}
