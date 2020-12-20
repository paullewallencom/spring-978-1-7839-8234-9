package com.springessentialsbook.chapter5.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by hamid on 08/10/15.
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
    String role_user;
    public GrantedAuthorityImpl(String role_user) {
        this.role_user=role_user;
    }

    public String getAuthority() {
        return role_user;
    }
}
