package com.scm.service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositry.ContactRepo;
import com.scm.service.ContactService;


@Service
public class ContactServiceImpl implements ContactService{
    @Autowired
    private ContactRepo contactRepo ;

    @Override
    public Contacts save(Contacts contact) {
       
        String contactId = UUID.randomUUID().toString() ;
        contact.setId(contactId) ;
        return contactRepo.save(contact) ;

    }

    @Override
    public Contacts update(Contacts contact) {
       
        var contactOld = contactRepo.findById(contact.getId())
            .orElseThrow(()-> new ResourceNotFoundException("Cannot find contact")) ;

            contactOld.setName(contact.getName()) ;
            contactOld.setEmail(contact.getEmail());
            contactOld.setPhoneno(contact.getPhoneno());
            contactOld.setAddress(contact.getAddress());
            contactOld.setDescription(contact.getDescription());
            contactOld.setFavourite(contact.isFavourite());
            contactOld.setContactImage(contact.getContactImage());
            contactOld.setWebsite_link(contact.getWebsite_link());
            contactOld.setLinkedin(contact.getLinkedin());
            contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
          

            return contactRepo.save(contactOld) ;

    }

    @Override
    public List<Contacts> getAll() {
       
        return contactRepo.findAll() ;
    }

    @Override
    public Contacts getById(String id) {
       
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(id+" not found")) ;
    }

    @Override
    public void delete(String id) {
       var contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(id+" not found")) ;

       contactRepo.delete(contact);
    }

   

    @Override
    public List<Contacts> getByUserId(String userId) {
         
        return contactRepo.findByUserId(userId) ;
    }

    @Override
    public Page<Contacts> getByUser(User user, int page, int size, String sortBy, String direction) {
        
        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending() ;
        var pageable = PageRequest.of(page, size, sort) ;
       return contactRepo.findByUser(user, pageable) ;
    }

    @Override
    public Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
        
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending() ;
        var pageable = PageRequest.of(page,size,sort) ;
        return contactRepo.findByUserAndNameContaining(user,nameKeyword, pageable) ;
    }

    @Override
    public Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending() ;
        var pageable = PageRequest.of(page,size,sort) ;
        return contactRepo.findByUserAndEmailContaining(user,emailKeyword, pageable) ;
    }

    @Override
    public Page<Contacts> searchByPhoneno(User user, String phonenoKeyword, int size, int page, String sortBy, String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending() ;
        var pageable = PageRequest.of(page,size,sort) ;
        return contactRepo.findByUserAndPhonenoContaining(user,phonenoKeyword, pageable) ;
    }

    



}
