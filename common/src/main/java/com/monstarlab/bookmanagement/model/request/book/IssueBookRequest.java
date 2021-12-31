package com.monstarlab.bookmanagement.model.request.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueBookRequest {
    @Min(value = 1, message = "validation.constraints.userId.NotNull.message")
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("book_ids")
    @Singular
    private Set<Long> bookIds;
}
