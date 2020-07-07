package com.xinyu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CusterService {

    @Autowired
    UserService userService;
    public void CusterService(){
        System.out.println("CusterService init");
    }

    public UserService getUserService() {
        System.out.println("CusterService luoji");
        return userService;
    }
}
