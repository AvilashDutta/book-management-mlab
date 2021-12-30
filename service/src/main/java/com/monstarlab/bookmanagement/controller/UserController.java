package com.monstarlab.bookmanagement.controller;

import com.monstarlab.bookmanagement.model.dto.user.UserDto;
import com.monstarlab.bookmanagement.model.request.user.UserCreateRequest;
import com.monstarlab.bookmanagement.model.request.user.UserUpdateRequest;
import com.monstarlab.bookmanagement.model.response.ApiResponse;
import com.monstarlab.bookmanagement.service.user.UserService;
import com.monstarlab.bookmanagement.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController{

    private final UserService userService;

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody @Valid UserCreateRequest request,
                                                           BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(userService.createUser(request)));
    }

    @PutMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@RequestBody @Valid UserUpdateRequest request,
                                                           BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(userService.updateUser(request)));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> deleteUser(@PathVariable long id){
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(userService.deleteUser(id)));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUser(){
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(userService.getAllUser()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable long id){
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(userService.getUserById(id)));
    }
}
