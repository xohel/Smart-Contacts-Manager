package com.scm.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

Optional<User>  findByeMail(String eMail) ;

Optional<User> findByEmailToken(String id) ;
   
}
