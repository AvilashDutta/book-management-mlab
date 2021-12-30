package com.monstarlab.bookmanagement.model.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String username;
    @NotBlank(message = "validation.constraints.user.email.empty.message")
    private String password;
    @NotBlank(message = "validation.constraints.user.fullName.NotNull.message")
    @JsonProperty("full_name")
    private String fullName;
    @Email(message = "validation.constraints.user.email.Invalid.message")
    private String email;
    @NotNull(message = "validation.constraints.user.role.empty.message")
    private List<String> roles;
}
