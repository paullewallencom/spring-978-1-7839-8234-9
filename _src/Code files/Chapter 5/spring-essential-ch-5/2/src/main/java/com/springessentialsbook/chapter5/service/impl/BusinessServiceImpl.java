package com.springessentialsbook.chapter5.service.impl;

import com.springessentialsbook.chapter5.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by hamid on 09/01/16.
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    UserDetailsService userDetailsService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public void migrateUsers() {
        //migrate user implementation
        System.out.println("migrateUsers>>>>>>");
    }

    public Object loadUserData(String username) {
        //load User Data  implementation
        System.out.println("loadUserData>>>>>>");
        return new Object();
    }

    public boolean isEligibleToSeeUserData(String loggedinUserName, String username) {
        UserDetails loggedinUserDetails=userDetailsService.loadUserByUsername(loggedinUserName);


        return hasAdminRole() ||  loggedinUserDetails.getUsername().equalsIgnoreCase(username);
    }


    private boolean hasAdminRole( ) {
        boolean  hasAdminRole=false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                hasAdminRole=true;
                break;
            }
        }

        return hasAdminRole;
    }


}
