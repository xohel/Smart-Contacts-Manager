package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.service.EmailService;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired 
	private EmailService service ;
	@Test 
	void sendEmailTest() {

		service.sendEmail(
			"junesohel@gmail.com", 
			"SMC Verification", 
			"click here to verify your account");


	}

}
