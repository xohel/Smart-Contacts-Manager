package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.scm.entities.User;
import com.scm.forms.UserForms;
import com.scm.helpers.MessageType;
import com.scm.helpers.message;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
    @Autowired
    private UserService userService ;


    @GetMapping("/")
    public String Index() {

        return "redirect:/home" ;
    }
    
    @RequestMapping("/home") 

    public String home(Model model) {

        System.out.println("home page handler");
        model.addAttribute("name", "springboot technologies" ) ;
    model.addAttribute("youtubeChannel", "sohels yt") ;
        return "home" ;
    }



// about 

@RequestMapping("/about") 
public String aboutPage(Model model){

        model.addAttribute("isLoggedIn", false) ;
    System.out.println("about page loading");
return "about" ;
} 


//services
@RequestMapping("/service") 
public String servicesPage(){

    System.out.println("services page loading");


return "service" ;
} 
// contact 
@GetMapping("/contact")
public String contact(){

    return "contact" ;
} 

@GetMapping("/login")
public String login(){

    return "login" ;
} 

@GetMapping("/signup")
public String signup(Model model){

  //  UserForms uForm = new UserForms() ;
    model.addAttribute("UserForm",new UserForms()) ;
    
    return "signup" ;
} 

// processing register / signup

@RequestMapping(value="/do-register" , method = RequestMethod.POST)
public String processRegister( @Valid @ModelAttribute("UserForm") UserForms UserForm, BindingResult result, HttpSession session) { 
   // System.out.println(UserForm); 
    //fetch the data - receive user data in com.scm.UserForms
    //validate the data
    if (result.hasErrors()) {
       
        message Message1 =  message.builder().content("Try again").type(MessageType.red).build() ;
        session.setAttribute("Message",Message1) ;
        return "signup";
    }

    //save to db

    User user = new User() ;
    user.setName(UserForm.getName()) ;
    user.setPassWord(UserForm.getPassword());
    user.setPhoneNo(UserForm.getPhoneNo());
    user.setInfo(UserForm.getInfo());
    user.setEMail(UserForm.getEMail());
    user.setProfilePic("https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg") ;
    user.setEnabled(false);
    

    User savedUser = userService.saveUser(user)  ;

     System.out.println("User saved : ");
    // message - registration successful
   
 message Message =  message.builder().content("Registration successful").type(MessageType.green).build() ;
    session.setAttribute("Message",Message) ;
    return "redirect:/signup" ;
     
    
    // redirect to login 


}


}