package com.monstarlab.bookmanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monstarlab.bookmanagement.model.dto.user.UserDto;
import com.monstarlab.bookmanagement.model.request.book.IssueBookRequest;
import com.monstarlab.bookmanagement.service.book.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
@Slf4j
class BookTests  {
	@Autowired
	private BookServiceImpl bookService;

	@Test
	public void issueBookRequest() throws JsonProcessingException {
		UserDto userDto= bookService.issueBook(new IssueBookRequest(
				1,
				new HashSet<>(1,2)
		));
		log.info("Book Issued Successful: {} ", new ObjectMapper().writeValueAsString(userDto));
	}
}
