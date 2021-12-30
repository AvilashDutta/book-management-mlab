package com.monstarlab.bookmanagement.service.auth;

import com.monstarlab.bookmanagement.model.request.auth.AuthenticationRequest;
import com.monstarlab.bookmanagement.model.response.auth.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.message.AuthException;

public interface AuthService {
    TokenResponse authenticate(AuthenticationRequest request);
    UserDetails validateToken(String token);
}
