package com.scm.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.repositry.UserRepository;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo ;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
      return  userRepo.findByeMail(username).orElseThrow(()-> new  UsernameNotFoundException("Inavalid username or password")) ;
    }



}
