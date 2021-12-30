package com.monstarlab.bookmanagement;

import com.monstarlab.bookmanagement.model.dto.email.EmailDto;
import com.monstarlab.bookmanagement.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

@SpringBootTest
@Slf4j
class BookManagementApplicationTests {
	@Autowired
	private EmailService emailService;

	@Test
	public void sendEmail() {
		try {
			emailService.send(EmailDto.builder()
					.email("test@gmail.com")
					.body("Mail sent for test")
					.build())
					.thenAcceptAsync(emilDto -> log.info("Mail sent Successful to {}", emilDto.getEmail()));
			log.info("Email sending in progress");
		} catch (InterruptedException e) {
			log.error("Exception occurred while sending email", e );
		}
	}

}
