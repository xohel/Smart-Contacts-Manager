package com.scm.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.* ;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity(name="user") //links to db
@Table(name="users") //rename the table, default is class name
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder



public class User implements UserDetails {

 
    @Id
    private String userId ;
    @Column(name = "user_Name", nullable = false)   
    private String name;
    @Column(unique = true, nullable = false) 
    private String eMail ;
    private String passWord ; 
    @Column(length = 500)
    private String info ; 
    private String profilePic ;
    private String phoneNo ;
    

    
    private boolean enabled = false ;
    
    private boolean emailVerified = false ;
    
    private boolean phoneNoVerified= false ;

    //signup by google / fb/ github/ linkedin 
    @Enumerated(EnumType.STRING)
    private providers provider = providers.SELF ;
    private String providerId; 
    
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)    
    private List<Contacts> Contacts= new ArrayList<> () ;
   
   
   
//   @ElementCollection(fetch = FetchType.EAGER) 
//     private List <String> RoleList = new ArrayList<>() ; 

//     public void setRoleList(List <String> RoleList) {

//         this.RoleList = RoleList ;
//     }


  //  @Override
  //  public Collection<? extends GrantedAuthority> getAuthorities() {
        
     // Collection<SimpleGrantedAuthority> roles =  roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList) ;
       // return  roles ;
    
   // }

    @Override
    public String getPassword() {
        return this.passWord ;
    }

    @Override
    public String getUsername() {
       return this.eMail ;
    } 

  
   String findByEmail(String name){

        return eMail ;
   }

  

    @Override
    public boolean isAccountNonExpired() {

        return true ;
           }

     @Override
    public boolean isAccountNonLocked() {
    return true ;
           }       


           @Override
           public boolean isCredentialsNonExpired() {
               return true ;
           }



         @ElementCollection(fetch = FetchType.EAGER) 
         private List <String> RoleList = new ArrayList<>() ;
        
         private String emailToken ;


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            
            Collection <SimpleGrantedAuthority>roles = RoleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList()) ;

            return roles ;
        
        }

        @Override
                public boolean isEnabled() {
                    return this.enabled;
                }
  

}
