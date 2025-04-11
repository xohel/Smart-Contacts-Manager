package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.scm.service.Impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {


    @Autowired 
    private SecurityCustomUserDetailService userDetailService ;

    
    @Autowired
    private OAuthenticationSuccessHandler handler ;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

   // private InMemoryUserDetailsManager inMemoryUserDetailsManager ;
    // @Bean
    // public UserDetailsService userDetailsService() {

    //   UserDetails user1 =  User
    //   .withDefaultPasswordEncoder()
    //   .username("admin123")
    //   .password("admin123")
    //   .roles("ADMIN","USER")
    //   .build() ;

    //   var  inMemoryUserDetailsManager = new InMemoryUserDetailsManager() ;

    //     return inMemoryUserDetailsManager ;
    // }
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider() ;





        //require userDetailService object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);

        //require passwordEncoder object 
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider ;

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //configuration

        //urls configured
        httpSecurity.authorizeHttpRequests(authorize->{
          //  authorize.requestMatchers("/home","/signup").permitAll()  ;
          authorize.requestMatchers("/user/**").authenticated();
          authorize.anyRequest().permitAll() ; 
        });

        //form default login
        httpSecurity.formLogin(formLogin->{

            formLogin.loginPage("/login") ;
            formLogin.loginProcessingUrl("/authenticate") ;
            formLogin.successForwardUrl("/user/profile") ;
          //  formLogin.failureForwardUrl("/login?error=true") ;
            formLogin.usernameParameter("email") ;
            formLogin.passwordParameter("password") ;


           formLogin.failureHandler(authenticationFailureHandler);


        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable) ; 
        httpSecurity.logout(logoutForm->{

            logoutForm.logoutUrl("/logout") ;
            logoutForm.logoutSuccessUrl("/login?logout=true") ;
        }) ;

        // oauth configurations
        httpSecurity.oauth2Login(oauth->{
        
            oauth.loginPage("/login") ;
            oauth.successHandler(handler);
         } ) ; 

        return httpSecurity.build() ;


    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder() ;
    }


}
