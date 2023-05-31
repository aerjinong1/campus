package com.example.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.campus.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Orders>{
    List<Orders> getordersList(int start,int end);

    Integer createOrders(int customer, String startPoint, String endPoint, int price, String orderInfo, Date createTime, int several);

    Orders getOrdersByOrderId(int orderId);

    List<Orders> getAllByStatusOrders();
    List<Orders> getOrdersListByUserId(int userId);

    void acceptOrder(int orderId,int postmanId);

}
