package org.ariel.ApiSchoolManagement.Utils.Jwt;

import java.util.Arrays;
import java.util.List;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.ariel.ApiSchoolManagement.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailService implements UserDetailsService {

    @Autowired
    private UsersService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = service.getByEmail(username);
        if(user == null) throw new UsernameNotFoundException(username + " does not exists");
        String roleName = user.getRole().getName();
        List<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(roleName));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
    
}
