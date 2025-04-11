package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.User;
import com.scm.entities.providers;
import com.scm.helpers.AppConstants;
import com.scm.repositry.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);

  @Autowired
  private UserRepository userRepository;

  @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        
                logger.info("OAuthenticationSuccessHandler");

              var oAuth2AuthenticationToken =  (OAuth2AuthenticationToken)authentication ;

              String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId() ;
              logger.info("client id : " + authorizedClientRegistrationId) ;

               var oauthUser = (DefaultOAuth2User) authentication.getPrincipal() ;

                oauthUser.getAttributes().forEach((key,value) -> {
                  System.out.println("oauthUser working");

                    logger.info(key+ " : " + value) ;

                });

                User user = new User() ;

                user.setUserId(UUID.randomUUID().toString());
                user.setRoleList(List.of(AppConstants.ROLE_USER));
                user.setEmailVerified(true );
                user.setEnabled(true);
                user.setPassWord("dummy");
                

              if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {

                // google attributes
                user.setEMail(oauthUser.getAttribute("email").toString());
                user.setProfilePic(oauthUser.getAttribute("picture").toString());
                user.setName(oauthUser.getAttribute("name").toString());
                user.setProviderId(oauthUser.getName());
                user.setProvider(providers.GOOGLE);
                user.setInfo("This account is created by google") ;

              }

              else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                  //github attributes

                 // String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString()+"@gmail.com" ;

                 // String picture = oauthUser.getAttribute("Avatar_url").toString() ;

                 // String name = oauthUser.getAttribute("login").toString() ;

                 // String providerId = oauthUser.getName() ; 

                 String name = oauthUser.getAttribute("name") != null
                 ? oauthUser.getAttribute("login").toString()
                 : oauthUser.getAttribute("login").toString();


                  String email = oauthUser.getAttribute("email") != null
                  ? oauthUser.getAttribute("email").toString()
                  : oauthUser.getAttribute("login").toString() + "@example.com";


                  String picture = oauthUser.getAttribute("avatar_url") != null
            ? oauthUser.getAttribute("avatar_url").toString()
            : "default-avatar-url";

                              user.setProviderId(oauthUser.getName());
                  user.setProvider(providers.GITHUB);

                  user.setName(name);
                    user.setEMail(email);
                    user.setProfilePic(picture);
                    user.setProviderId(oauthUser.getName());
                    user.setProvider(providers.GITHUB);
              }

              else 
              
                logger.info("OAuthenticationSuccessHandler: Unknown Provider");
          

/* 
              DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal() ;

            //     logger.info(user.getName());
            //    user.getAttributes().forEach((key, value)->{

            //     logger.info("{}={}", key, value) ;

            //     logger.info(user.getAuthorities().toString()) ;
            //  })  ;
 
             //getting user details

             String email = user.getAttribute("email").toString() ;
             String name = user.getAttribute("name").toString() ;
             String picture = user.getAttribute("picture").toString() ;
     
             //create user and save in db

                User user1 = new User() ;

                user1.setEMail(email);
                user1.setName(name);
                user1.setProfilePic(picture);
                user1.setPassWord("password") ;
                user1.setUserId(UUID.randomUUID().toString());
                user1.setProvider(providers.GOOGLE);
                user1.setEnabled(true);
                user1.setEmailVerified(true);
                user1.setProviderId(user.getName()) ;
                user1.setRoleList(List.of(AppConstants.ROLE_USER)) ;
              //  user1.setAbout("This account is created by google") ;
                
            User user2 = userRepository.findByeMail(email).orElse(null) ;

            if (user2 == null) {
                
                userRepository.save(user1) ;
                logger.info("user saved: " + email);

                            }
*/

          User user2 = userRepository.findByeMail(user.getEMail()).orElse(null) ;

          if (user2 == null) {
              
              userRepository.save(user) ;
              System.out.println("user saved" + user.getEMail());
          }
                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
          }  
        
    }

