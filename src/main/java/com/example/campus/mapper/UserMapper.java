package com.example.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.campus.entity.User;
import com.example.campus.util.JsonResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getall();

    Integer signup(String userName,String password);

    User login(String userName);

    User getUserInfo(int userId);

    Integer setImg(String img ,int userId);

    String getUserAddress(int userId);

    Integer addUserAddress(int userId,String address);
}
