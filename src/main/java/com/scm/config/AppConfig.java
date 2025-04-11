package com.scm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;



@Configuration
public class AppConfig {


    @Value("${cloudinary.cloud.name}")
    private String Cloudname ;
    @Value("${cloudinary.api.key}")
    private String Apikey   ;
    @Value("${cloudinary.api.secret}")
    private String Apisecret;

    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(
        ObjectUtils.asMap("cloud_name" , Cloudname,
        
        "api_key", Apikey,
        "api_secret", Apisecret 
        ) ) ;
    }
}
