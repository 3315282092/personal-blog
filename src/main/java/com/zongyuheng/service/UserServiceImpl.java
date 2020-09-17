package com.zongyuheng.service;

import com.zongyuheng.mapper.UserMapper;
import com.zongyuheng.pojo.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public TUser checkUser(String username, String password) {
        TUser user= userMapper.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public ArrayList<TUser> getUserList() {
        ArrayList<TUser> userList = userMapper.getUserList();
        return userList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println(username);
            TUser sysUser = userMapper.findByUsername(username);
       if (sysUser==null){
           System.out.println("问题在这里");
           return null;
       }
            System.out.println("异常出现在这里5");
            List<SimpleGrantedAuthority> authorityList=new ArrayList<>();
       if (sysUser.getType()==1) {
           System.out.println("异常出现在这里1");
           authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
       }else {
           System.out.println("异常出现在这里2");
           authorityList.add(new SimpleGrantedAuthority("ROLE_VISTOR"));

       }
            System.out.println("异常出现在这里3");
       UserDetails userDetails=new User(sysUser.getUsername(),sysUser.getPassword(),authorityList);
            System.out.println("问题在这个地方");
       return userDetails;
        }catch (Exception e){
            System.out.println("问题在这");
            return null;
        }
    }
}
