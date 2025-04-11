package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.service.UserService;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInfo(Model model, Authentication authentication) {

        if(authentication == null) return ;

        System.out.println("Adding user information to the model");

        String username = Helper.getEmailOfLoggeninUser(authentication);

        logger.info("User logged in : {}", username);

        User user = userService.getUserByEmail(username);
        System.out.println(user);

        System.out.println(user.getName());
        System.out.println(user.getEMail());

        model.addAttribute("loggedInUser", user);

    }

}
