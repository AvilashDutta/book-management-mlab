package com.monstarlab.bookmanagement.model.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monstarlab.bookmanagement.model.response.user.IssuedUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookResponse {
    private long id;
    private String name;

    @JsonProperty("author_name")
    private String authorName;

    private String description;

    @JsonProperty("no_of_copy")
    private int noOfCopy;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("issued_users")
    private List<IssuedUser> issuedUsers;
}
