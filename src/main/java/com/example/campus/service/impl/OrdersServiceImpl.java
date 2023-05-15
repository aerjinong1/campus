package com.example.campus.service.impl;

import com.example.campus.entity.Orders;
import com.example.campus.mapper.OrderMapper;
import com.example.campus.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Orders getOrdersByOrderId(int orderId) {
        return orderMapper.getOrdersByOrderId(orderId);
    }

    @Override
    public int createOrders(Orders orders) {
        return orderMapper.createOrders(orders.getStartPoint(),orders.getEndPoint(),orders.getPrice(),orders.getOrderInfo(),new Date(),orders.getSeveral());
    }

    @Override
    public List<Orders> getordersList(int from) {
        List<Orders> orders = orderMapper.getordersList(from,5);
        return orders;
    }


}
