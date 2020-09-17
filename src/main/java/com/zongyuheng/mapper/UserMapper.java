package com.zongyuheng.mapper;

import com.zongyuheng.pojo.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;


@Repository
@Mapper
public interface UserMapper {
    TUser findByUsernameAndPassword (String username, String password);
    ArrayList<TUser> getUserList();
    TUser findByUsername(@Param("username") String username);
}
