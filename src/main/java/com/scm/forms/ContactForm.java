package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContactForm {

     @NotBlank(message="Name is required")
    private String name ;
    @NotBlank(message="Email is required" )
    @Email(message ="Enter valid email")
    private String email ;
    @NotBlank(message="Contact number is required")
    @Size(max = 12, min=8 ,message="Enter valid Contact number")
    private String phoneno ;
    @NotBlank(message="Address is required")
   
    private String address ;
    @NotBlank(message="description is required")
   
    private String description ;
    
    private boolean favourite  ;
    private String websiteLink ;
    private String linkedin ;

    @ValidFile
   private MultipartFile  contactImage ; 

   private String picture ;


}
