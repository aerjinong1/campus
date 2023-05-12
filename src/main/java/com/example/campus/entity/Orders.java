package com.example.campus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    @TableId
    private Integer orderId;
    private String customer;
    private String orderName;
    private Integer several;//货物个数
    private Date createTime;
    private Date finishTime;
    private Integer postmanId;
    private Integer price;
    private String orderInfo;
    private String startPoint;
    private String endPoint;
    private String orderType;
    private String weight;

}
