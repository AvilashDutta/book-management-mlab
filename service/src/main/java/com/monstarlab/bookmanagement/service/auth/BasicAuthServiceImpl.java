package com.monstarlab.bookmanagement.service.auth;

import com.monstarlab.bookmanagement.exception.AuthException;
import com.monstarlab.bookmanagement.model.request.auth.AuthenticationRequest;
import com.monstarlab.bookmanagement.model.response.auth.TokenResponse;
import com.monstarlab.bookmanagement.util.JWTUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class BasicAuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public BasicAuthServiceImpl(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService,
                                AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenResponse authenticate(AuthenticationRequest request) {
        try{
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            );
            authenticationManager.authenticate(auth);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Bad Credential", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = JWTUtils.generateToken(userDetails.getUsername());
        return new TokenResponse(token);
    }

    @Override
    public UserDetails validateToken(String token){
        String userName = JWTUtils.extractUserName(token);

        if(JWTUtils.isTokenInvalidOrExpired(token))
            throw new AuthException("Authorization failed");

        return userDetailsService.loadUserByUsername(userName);
    }
}
