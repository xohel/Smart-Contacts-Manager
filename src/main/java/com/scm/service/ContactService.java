package com.scm.service;

import java.util.List;

import org.springframework.data.domain.Page;


import com.scm.entities.Contacts;
import com.scm.entities.User;

public interface ContactService {

     //save contacts in db

    Contacts save(Contacts contact) ;

    //update contact

    Contacts update(Contacts contact);

    //get contacts
    List<Contacts> getAll() ;

    //get contact by id

    Contacts getById(String id) ;

    //delete contact
    void delete(String id) ;

    //search contact
    Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) ;
    Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) ;
    Page<Contacts> searchByPhoneno( User user, String phonenoKeyword, int size, int page, String sortBy, String order) ;

    //get all contact of user by id
    List<Contacts> getByUserId(String userId) ;

    Page<Contacts> getByUser(User user, int page, int size, String sortField,String sortDirection) ;
}
