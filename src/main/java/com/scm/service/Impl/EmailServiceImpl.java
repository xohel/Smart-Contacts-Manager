package com.scm.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    
    @Autowired
   private JavaMailSender eMailSender ;
    @Value("${spring.mail.properties.domain_name}")
   private String domainName ;

    @Override
    public void sendEmail(String to, String subject, String body) {
       SimpleMailMessage message = new SimpleMailMessage();

       message.setTo(to);
       message.setSubject(subject);
       message.setText(body);
       message.setFrom(domainName);
       eMailSender.send(message) ;
    }

    
}
