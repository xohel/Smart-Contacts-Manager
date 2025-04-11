package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.forms.UserForms;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositry.UserRepository;
import com.scm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    User user = new User() ;

    // user dashboard page
    @RequestMapping("/dashboard")

    public String userDashboard() {

        return "user/dashboard";
    }

    // user profile page

    @RequestMapping("/profile")

    public String userProfile(Model model, Authentication authentication) {

       UserForms userForms = new UserForms();

       user.setName(userForms.getName());

       model.addAttribute("UserForms", userForms) ;

        
        return "user/profile";
    }

    // user contact page
    // user contact page
    // user add contact page
    // user delete contact page
    // user view contact page
    // user search contact page

}
