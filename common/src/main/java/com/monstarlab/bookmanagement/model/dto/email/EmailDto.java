package com.monstarlab.bookmanagement.model.dto.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailDto {
    private String email;
    private String body;
}
