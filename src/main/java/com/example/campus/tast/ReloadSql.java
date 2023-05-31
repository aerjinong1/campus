package com.example.campus.tast;

import com.example.campus.controller.OrderController;
import com.example.campus.entity.Orders;
import com.example.campus.mapper.OrderMapper;
import com.example.campus.util.RedisUtils1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
//@Configuration
@EnableScheduling
public class ReloadSql {
    @Autowired
    @Qualifier("redisUtils1")
    private RedisUtils1 redisUtils1;
    @Autowired
    OrderMapper orderMapper;
    @Scheduled(cron = "1 * * * * ?")
    public void updateRedisOfSQL(){
        redisUtils1.remove("orderList");//删除orderList
        List<Orders> allByStatusOrders = orderMapper.getAllByStatusOrders();//数据库获取信息
        System.out.println(allByStatusOrders.get(1).getClass());
        for (int i = 0; i < allByStatusOrders.toArray().length; i++) {
            redisUtils1.lPush("orderList", allByStatusOrders.get(i));//写入redis
        }
        System.out.println(redisUtils1.lRange("orderList", 0, -1));
    }
}
