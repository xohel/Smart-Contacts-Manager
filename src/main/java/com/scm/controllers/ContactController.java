package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.MessageType;
import com.scm.helpers.message;
import com.scm.service.ContactService;
import com.scm.service.ImageService;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/user/contact")
public class ContactController {

  private  Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class) ;
  private  Logger logger1 = org.slf4j.LoggerFactory.getLogger(ContactController.class) ;

    @Autowired 
    private ContactService contactService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private ImageService imageService ;

    //add contact page handler
    @RequestMapping("/add_contact")
    public String addContactView(Model model){

        ContactForm contactForm = new ContactForm() ;

        model.addAttribute("contactForm", contactForm) ; 
    //    contactForm.setWebsiteLink("google.com");
    //   contactForm.setLinkedin("facebook.com");
        return "user/add_contact" ;
    }
    
  
    
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result , Authentication authentication, HttpSession session){
       
        // validate the form : todo
        //process the form
        //get user after authentication

        if (result.hasErrors()) {

            message Message1 =  message.builder().content("Try again").type(MessageType.red).build() ;
            session.setAttribute("Message",Message1) ;
            
            return "user/add_contact" ;
        }

        String username = Helper.getEmailOfLoggeninUser(authentication) ;

       User user = userService.getUserByEmail(username) ;
        // convert form to contact

        // process the picture - todo

        logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename() );

        //image save in db

      

        Contacts contact = new Contacts() ;

        contact.setName(contactForm.getName());
        contact.setFavourite(contactForm.isFavourite()) ;
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneno(contactForm.getPhoneno()) ;
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription()) ;
        contact.setUser(user);
        contact.setWebsite_link(contactForm.getWebsiteLink());
        contact.setLinkedin(contactForm.getLinkedin()) ;

       if (contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {
        
        String Filename = UUID.randomUUID().toString() ;
        String fileURL =  imageService.uploadImage(contactForm.getContactImage(),Filename) ;
        contact.setContactImage(fileURL);
        contact.setCloudinaryImagePublicId(Filename) ; 
        
       }



        //save data to db
    
        contactService.save(contact) ;
        System.out.println(contactForm);
        //set contact picture url

        // set message contact save success or fail - todo

        message Message =  message.builder().content("Contact added successfully").type(MessageType.green).build() ;
    session.setAttribute("Message",Message) ;
        return "redirect:/user/contact/add_contact";
    }

    // view contacts
    @RequestMapping
    public String viewContacts(
    @RequestParam(value ="page", defaultValue = "0") int Page, 
    @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
    @RequestParam(value = "sortBy", defaultValue = "name") String SortBy, 
    @RequestParam(value = "direction", defaultValue = "asc") String direction    
    ,Model model , Authentication authentication) {

        String username = Helper.getEmailOfLoggeninUser(authentication) ;

        User user = userService.getUserByEmail(username) ;

      //  contactService.getByUser(user) ;

        Page<Contacts> pageContacts = contactService.getByUser(user,Page,size,SortBy, direction) ; 

        model.addAttribute("pageContacts", pageContacts) ;
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE) ;

        model.addAttribute("contactSearchForm", new ContactSearchForm()) ;

    
        
        return "user/contact" ;
    }


    @RequestMapping("/search")
    public String searchHandler(

       @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value="size", defaultValue = AppConstants.PAGE_SIZE + "")int size,
        @RequestParam(value="page", defaultValue = "0") int page,
        @RequestParam(value="sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value="direction",defaultValue="asc")String direction, Model model
        , Authentication authentication
         ){


            logger1.info("fields {} keyword {}" , contactSearchForm.getFields() , contactSearchForm.getValue()) ;
            var user = userService.getUserByEmail(Helper.getEmailOfLoggeninUser(authentication)) ;

            Page<Contacts> pageContact = null ;
            if(contactSearchForm.getFields().equalsIgnoreCase("name") ) {

             pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page,sortBy, direction, user) ;
            }

           else if(contactSearchForm.getFields().equalsIgnoreCase("email")) {

            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page,sortBy, direction, user) ;
           }
           else if(contactSearchForm.getFields().equalsIgnoreCase("phoneno")) {

            pageContact = contactService.searchByPhoneno(user, contactSearchForm.getValue(), size, page,sortBy, direction) ;
           }

           logger1.info("pageContact {}", pageContact) ;

           model.addAttribute("contactSearchForm", contactSearchForm) ;
           model.addAttribute("pageContact" , pageContact) ;
           model.addAttribute("pageSize", AppConstants.PAGE_SIZE) ;
           
          
        return "user/search_user" ;
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
        @PathVariable("contactId") String contactId, HttpSession session) {

            contactService.delete(contactId);
            logger.info("contactId{} deleted", contactId) ;

            message Message =  message.builder().content("Contact deleted successfully").type(MessageType.green).build() ;
    session.setAttribute("Message",Message) ;


            return "redirect:/user/contact" ;
        }


@GetMapping("/view/{contactId}")
public String updateContactFormView(@PathVariable ("contactId") String contactId, Model model ) {


var contact = contactService.getById(contactId) ;
ContactForm contactForm = new ContactForm() ;
contactForm.setName(contact.getName());
contactForm.setEmail(contact.getEmail());
contactForm.setPhoneno(contact.getPhoneno());
contactForm.setAddress(contact.getAddress());
contactForm.setFavourite(contact.isFavourite());
contactForm.setDescription(contact.getDescription());
contactForm.setLinkedin(contact.getLinkedin());
contactForm.setWebsiteLink(contact.getWebsite_link());
contactForm.setPicture(contact.getContactImage());
model.addAttribute("contactForm", contactForm) ;
model.addAttribute("contactId", contactId) ;




return "user/update_contact_view" ;
        }
       
        @RequestMapping(value="/update/{contactId}" , method = RequestMethod.POST)
public String updateContact(@PathVariable("contactId") String contactId, @Valid @ModelAttribute ContactForm contactForm,
BindingResult result,
Model model, HttpSession session) {

    if (result.hasErrors()) {
        return "user/update_contact_view"  ;
    }
    var con = contactService.getById(contactId);

    con.setName(contactForm.getName());
    con.setEmail(contactForm.getEmail());
    con.setPhoneno(contactForm.getPhoneno());
    con.setAddress(contactForm.getAddress());
    con.setDescription(contactForm.getDescription());
    con.setFavourite(contactForm.isFavourite());
    con.setWebsite_link(contactForm.getWebsiteLink());
    con.setLinkedin(contactForm.getLinkedin());
    con.setId(contactId);

    //process image

    if (contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {
        String FileName = UUID.randomUUID().toString();
    String imageURL=imageService.uploadImage(contactForm.getContactImage(), FileName);
    con.setCloudinaryImagePublicId(FileName);
    con.setContactImage(imageURL);
    contactForm.setPicture(imageURL);
    }
    

    var updateCon = contactService.update(con) ;

    logger.info("Updated contact {}", updateCon) ;

    message Message =  message.builder().content("Contact updated successfully").type(MessageType.green).build() ;
    session.setAttribute("Message",Message) ;
    return "redirect:/user/contact/view/" + contactId  ;
}

}
