package com.zongyuheng.service;

import com.zongyuheng.pojo.TUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


public interface UserService extends UserDetailsService {
    TUser checkUser(String username,String password);
    ArrayList<TUser> getUserList();
}
