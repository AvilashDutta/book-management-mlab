package com.monstarlab.bookmanagement.controller;

import com.monstarlab.bookmanagement.exception.BadRequestException;
import com.monstarlab.bookmanagement.helper.LocaleMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class BaseController {

    @Autowired
    private LocaleMessageHelper messageHelper;

    private String getJoinedErrorMessage(BindingResult bindingResult){
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .map(messageHelper::getLocalMessage)
                .collect(Collectors.joining(", "));
    }

    protected void throwIfHasError(BindingResult result){
        if(result.hasErrors())
            throw new BadRequestException(this.getJoinedErrorMessage(result));
    }
}
