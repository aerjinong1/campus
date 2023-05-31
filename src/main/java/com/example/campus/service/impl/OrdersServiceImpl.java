package com.example.campus.service.impl;


import com.example.campus.entity.Orders;
import com.example.campus.mapper.OrderMapper;
import com.example.campus.service.OrdersService;
import com.example.campus.util.RedisUtils1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;



@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    @Qualifier("redisUtils1")
    private RedisUtils1 redisUtils1;

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Orders getOrdersByOrderId(int orderId) {
        return orderMapper.getOrdersByOrderId(orderId);
    }

    @Override
    public int createOrders(Orders orders) {

        TimeZone time = TimeZone.getTimeZone("Etc/GMT-8");  //转换为中国时区

        TimeZone.setDefault(time);
        return orderMapper.createOrders(orders.getCustomer(),orders.getStartPoint(),orders.getEndPoint(),orders.getPrice(),orders.getOrderInfo(),new Date(),orders.getSeveral());
    }

    @Override
    public void acceptOrder(int ordersId, int postmanId) {
        orderMapper.acceptOrder(ordersId,postmanId);
    }

    @Override
    public List<Orders> getordersList(int pages) {
        List<Orders> orders = orderMapper.getordersList(pages,5);
        return orders;
    }

    @Override
    public List<Orders> getOrdersListByUserId(int userId) {

        List<Orders> orders = orderMapper.getOrdersListByUserId(userId);
        System.out.println(orders.size());
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(orders.get(i));
            redisUtils1.lPush("orderList:user:" + userId, orders.get(i));
        }
        return orders;
    }


}
