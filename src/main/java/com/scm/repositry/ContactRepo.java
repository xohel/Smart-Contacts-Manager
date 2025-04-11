package com.scm.repositry;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contacts;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contacts,String> {

   //method to find contacts by user
    //using custom finder method
   Page<Contacts> findByUser(User user, Pageable pageable) ;
    //using custom query method 
   @Query("SELECT c FROM Contacts c WHERE c.user.Id = :userId") 
   List<Contacts> findByUserId(@Param("userId")String userId) ;

   Page<Contacts> findByUserAndNameContaining(User user,String namekeyword, Pageable pageable) ; // small k in namekeyword and name same as in contacts.java
   Page<Contacts> findByUserAndEmailContaining(User user,String emailkeyword, Pageable pageable) ;
   Page<Contacts> findByUserAndPhonenoContaining(User user,String Phonenokeyword, Pageable pageable) ;

}
