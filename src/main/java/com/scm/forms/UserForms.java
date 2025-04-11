package com.scm.forms;

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


public class UserForms {


    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String name ;
    @Email (message = " Enter valid Email")
    @NotBlank(message = "Email is required")
    private String eMail ;
    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String password ;
    @NotBlank(message = "Phone number is required")
    @Size(min = 6, max = 12, message = "Min 3 characters required")
    private String phoneNo ;
    @NotBlank(message = "Info is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String info ; 
  //  private String profilePic ;

  

}
