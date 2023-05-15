package com.example.campus.service;

import com.example.campus.entity.Orders;
import org.springframework.core.annotation.Order;

import java.util.List;

public interface OrdersService {
    Orders getOrdersByOrderId(int orderId);

    int createOrders(Orders orders);



    List getordersList(int from);
}
