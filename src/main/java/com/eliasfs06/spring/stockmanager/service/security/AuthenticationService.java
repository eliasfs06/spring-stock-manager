package com.eliasfs06.spring.stockmanager.service.security;

import com.eliasfs06.spring.stockmanager.model.dto.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private MyUserDetailsService userDetailsService;


    public Authentication authenticateUser(AuthenticationDTO data){
        UserDetails userDetails = userDetailsService.loadUserByUsername(data.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, data.getPassword(), userDetails.getAuthorities());
        Authentication auth = authenticationManager.authenticate(authentication);
        return auth;
    }

}