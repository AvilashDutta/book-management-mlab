package com.monstarlab.bookmanagement.service.email;

import java.util.concurrent.CompletableFuture;
import com.monstarlab.bookmanagement.model.dto.email.EmailDto;

public interface EmailService {
    CompletableFuture<EmailDto> send(EmailDto emailDto) throws InterruptedException;
}
