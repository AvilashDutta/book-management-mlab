package com.monstarlab.bookmanagement;

import com.monstarlab.bookmanagement.model.request.user.UserCreateRequest;
import com.monstarlab.bookmanagement.props.AppProperties;
import com.monstarlab.bookmanagement.service.RoleEntityService;
import com.monstarlab.bookmanagement.service.UserEntityService;
import com.monstarlab.bookmanagement.service.role.RoleService;
import com.monstarlab.bookmanagement.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final AppProperties props;

    private final RoleEntityService roleEntityService;
    private final RoleService roleService;
    private final UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(props.isSeederEnabled()){
            populateRoles();
            populateUsers();
        }
    }

    private void populateUsers() {
        if(CollectionUtils.isEmpty(userService.getAllUser()))
            userService.createUser(
                    new UserCreateRequest(
                            "admin",
                            "admin",
                            "Admin",
                            "admin@gmail.com",
                            List.of(
                                    "ADMIN",
                                    "USER"
                            )
                    )
            );
    }

    private void populateRoles() {
        if(CollectionUtils.isEmpty(roleEntityService.findAll()))
            roleService.createRoles(List.of(
                    "ADMIN",
                    "USER"
            ));
    }}
