package com.monstarlab.bookmanagement.service;

import com.monstarlab.bookmanagement.entity.RoleEntity;
import com.monstarlab.bookmanagement.exception.EmailException;
import com.monstarlab.bookmanagement.exception.RecordNotFoundException;
import com.monstarlab.bookmanagement.helper.LocaleMessageHelper;
import com.monstarlab.bookmanagement.model.auth.CurrentUser;
import com.monstarlab.bookmanagement.model.dto.email.EmailDto;
import com.monstarlab.bookmanagement.props.AppProperties;
import com.monstarlab.bookmanagement.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
public abstract class BaseService {

    protected final String RECORD_NOT_FOUND_MSG_KEY="api.response.NOT_FOUND.message";

    @Autowired
    protected LocaleMessageHelper messageHelper;
    @Autowired
    protected AppProperties props;
    @Autowired
    private EmailService emailService;

    protected String getLocaleMessage(String messageKey){
        return messageHelper.getLocalMessage(messageKey);
    }

    protected void sendEmail(EmailDto emailDto){
        try {
            emailService.send(emailDto)
                    .thenAcceptAsync(emilDto -> log.info("Mail sent Successful to {}", emilDto.getEmail()));
            log.info("Email sending in progress");
        } catch (InterruptedException e) {
            log.error("Exception occurred while sending email", e );
        }
    }

    protected CurrentUser getCurrentUser() {
        return (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
