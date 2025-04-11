package com.scm.entities;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class Contacts {

    @Id
    private String id ;
    private String name ;
    private String email ;
    private String phoneno ;
    private String address ;
    private String contactImage ;
    @Column(length=500)  
    private String description ;
    private boolean favourite = false ;
    private String website_link ;
    private String linkedin ;

    private String CloudinaryImagePublicId ;
  //  public String picture ;
    




   // private List<String> SocialLinks = new ArrayList<>() ;

   @ManyToOne
   @JsonIgnore
   private User user ;

    @OneToMany(mappedBy = "Contacts", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLinks> links = new ArrayList<>();

    
    

}
