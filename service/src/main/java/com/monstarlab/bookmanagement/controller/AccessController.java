package com.monstarlab.bookmanagement.controller;

import com.monstarlab.bookmanagement.model.request.auth.AuthenticationRequest;
import com.monstarlab.bookmanagement.model.response.auth.TokenResponse;
import com.monstarlab.bookmanagement.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccessController {

    private final AuthService authService;

    @PostMapping("/access/token")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
