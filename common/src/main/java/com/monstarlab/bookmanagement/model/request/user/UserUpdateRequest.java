package com.monstarlab.bookmanagement.model.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Min(value = 1, message = "validation.constraints.userId.NotNull.message")
    private long id;
    @NotBlank(message = "validation.constraints.user.fullName.NotNull.message")
    @JsonProperty("full_name")
    private String fullName;
    @Email(message = "validation.constraints.user.email.Invalid.message")
    @NotBlank(message = "validation.constraints.user.email.empty.message")
    private String email;
}
