package com.monstarlab.bookmanagement.enums;

import com.monstarlab.bookmanagement.exception.RecordNotFoundException;
import com.monstarlab.bookmanagement.util.StreamUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    ADMIN("ADMIN","ROLE_ADMIN"),
    USER("USER","ROLE_USER");

    private String name;
    private String value;

    private static UserRoleEnum find(final Predicate<UserRoleEnum> predicate) {
        return StreamUtils.find(Arrays.stream(UserRoleEnum.values()), predicate)
                .orElseThrow(() -> new RecordNotFoundException("No Role Found for this argument"));
    }

    public static String getValueByName(String name){
        return find(x-> Objects.equals(x.name, name))
                .getValue();
    }
}
