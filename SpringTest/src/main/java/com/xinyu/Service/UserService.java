package com.xinyu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    CusterService custerService;
    public void UserService(){
        System.out.println("UserService init");
    }

    public CusterService getCusterService() {
        return custerService;
    }
    public String getCusterService(String a) {
        return null;
    }
}
