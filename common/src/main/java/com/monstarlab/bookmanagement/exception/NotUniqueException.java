package com.monstarlab.bookmanagement.exception;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class NotUniqueException extends BadRequestException {

    public NotUniqueException(String message) {
        super(message);
    }
}
