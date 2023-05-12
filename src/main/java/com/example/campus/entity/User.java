package com.example.campus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId
    private Integer userId;
    private String userName;
    private String password;
    private String college;
    private Integer age;
    private Integer gender;
    private Integer listId;
    private String email;
    private String img;
    private String address;
}
