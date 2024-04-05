package com.eliasfs06.spring.stockmanager.service.security;

import com.eliasfs06.spring.stockmanager.model.User;
import com.eliasfs06.spring.stockmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not exists by username");
        }
        return user;
    }
}

