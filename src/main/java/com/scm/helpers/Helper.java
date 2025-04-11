package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggeninUser(Authentication authentication) {

      // AuthenticationPrincipal principal = (AuthenticationPrincipal)authentication.getPrincipal() ;

        if(authentication instanceof OAuth2AuthenticationToken) {

         var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken)  authentication ;

            var cliendId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId() ;

                var oauth2User = (OAuth2User)authentication.getPrincipal() ;
                String username = "" ;

            if(cliendId.equalsIgnoreCase("google")) {

                // sign in with googel

                System.out.println("Getting clientId from Google");
               username = oauth2User.getAttribute("email").toString() ;
            }

            else if(cliendId.equalsIgnoreCase("github")) {
                
                // sign in with github
                System.out.println("Getting clientId from Github");
               username = oauth2User.getAttribute("email") != null
                ? oauth2User.getAttribute("email").toString()
                : oauth2User.getAttribute("login").toString() + "@example.com";
            
            }
            return username ;



            // sign in with self 

        

        } else 
        {
            System.out.println("Getting data from local database");
           return authentication.getName() ;
            // return authentication.getName() ; 
        }

    }

    public static String getLinkForEmailVerification(String emailToken) {

        String link = "http://localhost:8081/auth/verify-email?token=" + emailToken ;

        return link ;
    }
}
