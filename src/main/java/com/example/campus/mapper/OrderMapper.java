package com.example.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.campus.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Orders>{
    List<Orders> getallorder();

    Integer createOrders(String startPoint, String endPoint, int price, String orderInfo, Date createTime, int several);

    Orders getOrdersByOrderId(int orderId);


}
