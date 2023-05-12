package com.example.campus.service;

import com.example.campus.entity.Orders;

public interface OrdersService {
    Orders getOrdersByOrderId(int orderId);

    int createOrders(Orders orders);
}
