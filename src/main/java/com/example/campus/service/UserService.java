package com.example.campus.service;

import com.example.campus.entity.User;
import com.example.campus.util.JsonResult;

import java.util.List;
import java.util.StringJoiner;

public interface UserService {
    List<User> getall();
    Integer signup(String userName,String password);

    User login(String userName ,String password);

    User getUserInfo(int userId);

    Integer setImg(String img,int id);

    String getAddress(int userId);
    Integer addAddress(int userId,String address);
}
