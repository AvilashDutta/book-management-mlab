package com.monstarlab.bookmanagement.service.auth;

import com.monstarlab.bookmanagement.entity.UserEntity;
import com.monstarlab.bookmanagement.exception.RecordNotFoundException;
import com.monstarlab.bookmanagement.service.BaseService;
import com.monstarlab.bookmanagement.service.UserEntityService;
import com.monstarlab.bookmanagement.service.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService extends BaseService implements UserDetailsService {

    private final UserEntityService userEntityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityService
                .findUserByUsername(username)
                .orElseThrow(()-> new RecordNotFoundException(
                        super.getLocaleMessage("validation.constraints.username.NotFound.message")));

        return UserMapper.mapToUserDetails(user);

    }

}
