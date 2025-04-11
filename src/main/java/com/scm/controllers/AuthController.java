package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helpers.MessageType;
import com.scm.helpers.message;
import com.scm.repositry.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    // verify link
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify-email")
    public String verifyEmail( @RequestParam("token") String tokens, HttpSession session) {

      User user =  userRepository.findByEmailToken(tokens).orElse(null) ;

      if(user !=null) {

        if(user.getEmailToken().equals(tokens)) {

            user.setEmailVerified(true);
            user.setEnabled(true);
            userRepository.save(user) ;

             message Message1 =  message.builder().content("Congratulations! your email is verified").type(MessageType.green).build() ;
            session.setAttribute("Message",Message1) ;
            return "success_page" ;
        }

        else {
         message Message1 =  message.builder().content("Oh no! Your Email is not verified. Try again").type(MessageType.red).build() ;
            session.setAttribute("Message",Message1) ;
        return "error_page" ;
        }
      }

      else {
        message Message1 =  message.builder().content("Oh no! Your Email is not verified. Try again").type(MessageType.red).build() ;
           session.setAttribute("Message",Message1) ;
       return "error_page" ;
       }

        
    }

}
