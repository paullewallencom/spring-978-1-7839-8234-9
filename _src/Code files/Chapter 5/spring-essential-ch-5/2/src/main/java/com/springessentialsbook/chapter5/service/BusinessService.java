package com.springessentialsbook.chapter5.service;


import org.springframework.security.core.userdetails.User;

public interface BusinessService {



    void migrateUsers();
    Object loadUserData(String username);
    boolean isEligibleToSeeUserData(String loggedinUserName, String username);
}
