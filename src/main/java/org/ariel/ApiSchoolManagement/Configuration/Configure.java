package org.ariel.ApiSchoolManagement.Configuration;

import java.security.Key;

import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Repository.IRollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
public class Configure {
   
    @Autowired
	private IRollRepository rollRepository;
    
    @Bean
    public PasswordEncoder passwordEncoderBean(){
        return new BCryptPasswordEncoder();
    }

    @Bean
	public void insertRoll(){
		String[] names = { "HEAD_MASTER", "TEACHER", "STUDENT" };
		for(String name: names){
			if(!rollRepository.existsByName(name)) rollRepository.save(new Roll(name));
		}
	}

	@Bean
	public Key keyBean(){
		return Keys.secretKeyFor(SignatureAlgorithm.HS256); 
	}

}
