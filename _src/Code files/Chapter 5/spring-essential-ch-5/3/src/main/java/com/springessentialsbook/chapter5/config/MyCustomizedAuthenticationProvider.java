package com.springessentialsbook.chapter5.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MyCustomizedAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsService userService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
           User user=null;
           Authentication auth=null;
           String username=authentication.getName();
           String password=authentication.getCredentials().toString();



            user= (User) userService.loadUserByUsername(username);
            if(password ==null ||   ! password.equals(user.getPassword())) throw new UsernameNotFoundException("wrong user/password");


        if(user !=null){
            auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        } else throw new UsernameNotFoundException("wrong user/password");

        return auth;
    }

    public boolean supports(Class<?> aClass) {
        return true;
    }
}
