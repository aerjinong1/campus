package com.example.campus.service.impl;

import com.example.campus.entity.User;
import com.example.campus.mapper.UserMapper;
import com.example.campus.service.UserService;
import com.example.campus.service.ex.loginException;
import com.example.campus.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.campus.controller.BaseController.OK;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer signup(String userName, String password) {
        userMapper.signup(userName, password);
        return null;
    }

    @Override
    public User login(String userName, String password) {
        User login = userMapper.login(userName);
        System.out.println(login);

        if (login == null) {//用户名不存在
            System.out.println("============================");
            throw new loginException("用户名不存在");
//            return new JsonResult<>(1002);
        }
        if (login.getPassword().equals(password)) {//密码匹配
            return login;
        } else {//密码错误
            throw new loginException("密码错误");
//            return new JsonResult<>(1001);
        }
    }

    @Override
    public User getUserInfo(int userId) {
        return userMapper.getUserInfo(userId);
    }

    @Override
    public Integer setImg(String img, int id) {
        userMapper.setImg(img, id);
        return null;
    }

    @Override
    public Integer addAddress(int userId, String address) {
        String userAddress = userMapper.getUserAddress(userId);
        System.out.println(userAddress + "+++++++++++++++++++++++++++++");
        String newAddress;
        if (userAddress != null&&!userAddress.equals("")) {
            newAddress = userAddress.split("]")[0] + "," + address + "]";
        } else {
            System.out.println("null");
            newAddress = "[" + address + "]";
        }

        Integer res = userMapper.addUserAddress(userId, newAddress);
        return res;
    }

    @Override
    public String getAddress(int userId) {
        userMapper.getUserAddress(userId);
        return userMapper.getUserAddress(userId);
    }

    @Override
    public List<User> getall() {
        List<User> getall = userMapper.getall();
        return getall;
    }
}
