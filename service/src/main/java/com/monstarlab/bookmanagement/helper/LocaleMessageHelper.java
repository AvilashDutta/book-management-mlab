package com.monstarlab.bookmanagement.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LocaleMessageHelper {

    private final HttpServletRequest request;
    private final MessageSource messageSource;

    public String getLocalMessage(String key, Object... objects) {
        return messageSource.getMessage(key, objects, request.getLocale());
    }


}
