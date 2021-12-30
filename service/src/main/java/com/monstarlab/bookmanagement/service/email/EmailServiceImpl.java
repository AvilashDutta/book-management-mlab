package com.monstarlab.bookmanagement.service.email;

import com.monstarlab.bookmanagement.model.dto.email.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{
    @Override
    @Async("emailExecutor")
    public CompletableFuture<EmailDto> send(EmailDto emailDto) throws InterruptedException {
        log.info("Email sending to {}",emailDto.getEmail());
        return CompletableFuture.completedFuture(emailDto);
    }
}
