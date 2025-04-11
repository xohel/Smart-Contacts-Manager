package com.scm.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositry.UserRepository;
import com.scm.service.EmailService;
import com.scm.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository UserRepository ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private EmailService emailService ;

    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    @Override
    public User saveUser(User user) {

        //generate userid before saving
        String userId = UUID.randomUUID().toString() ;

        user.setUserId(userId);
        //encoding the password
        user.setPassWord(passwordEncoder.encode(user.getPassword()));

        //set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER)) ;

       

        String emailToken = UUID.randomUUID().toString() ;

        user.setEmailToken(emailToken);

        User savedUser = UserRepository.save(user) ;

        String emailLink = Helper.getLinkForEmailVerification(emailToken) ;

        emailService.sendEmail(savedUser.getEMail(), "Verify Account: SCM ", emailLink) ;

        return savedUser ;
        
     
    }

    @Override
    public Optional<User> getUserById(String id) {
        
        return UserRepository.findById(id) ;
    }

    @Override
    public Optional<User> updateUser(User user) {
        
        User user2 = UserRepository.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found")  ) ;
        
        user2.setName(user.getName());
        user2.setEMail(user.getEMail());
        user2.setPassWord(user.getPassword());
        user2.setPhoneNo(user.getPhoneNo());
        user2.setInfo(user.getInfo());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneNoVerified(user.isPhoneNoVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderId(user.getProviderId());

        // save user in db

        User save = UserRepository.save(user2) ;

        return Optional.ofNullable(save) ;
    }

    @Override
    public void deleteUser(String id) {
            
        User user2 = UserRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found")  ) ; 
        
        UserRepository.delete(user2);
    }

    @Override
    public boolean isUserExists(String userId) {
        User user2 = UserRepository.findById(userId).orElse(null) ;

        return user2!=null ? true : false ; 
    }

    // @Override
    // public boolean isUserExistsByEmail(String EMail) {

    //   User user2 =  UserRepository.findByEmail(EMail).orElse(null) ;

    //   return user2 !=null ? true : false ;  
    // }

    @Override
    public List<User> getAllUsers() {
        
    
        return  UserRepository.findAll() ;
     
    }

    @Override
    public User getUserByEmail(String email) {
        
        return UserRepository.findByeMail(email).orElse(null);
    
    }

  

}
