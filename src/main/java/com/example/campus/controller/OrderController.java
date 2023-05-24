package com.example.campus.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.campus.entity.Orders;
import com.example.campus.service.impl.OrdersServiceImpl;
import com.example.campus.util.JsonResult;
import com.example.campus.util.RedisUtils1;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class OrderController extends BaseController{
    @Autowired
    @Qualifier("redisUtils1")
    private RedisUtils1 redisUtils1;
    public static final int OK = 200;

    @Value("${jwt.secretKey}")
    private String secrets;

    @Autowired
    private OrdersServiceImpl ordersService;

    @RequestMapping("getOrdersByOrderId")
    @ResponseBody
    public JsonResult<Orders> getOrdersByOrderId(int ordersId){
        Orders res = ordersService.getOrdersByOrderId(ordersId);
        return new JsonResult<Orders>(OK,res);
    }

    @RequestMapping("createOrders")
    @ResponseBody
    public JsonResult<Orders> createOrders(HttpSession session, @RequestBody String orders){


        JSONObject jsonObject = JSON.parseObject(orders);
        Orders orders1 = jsonToOrders(orders);
        ordersService.createOrders(orders1);
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken!=null){
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(secrets)
                    .parseClaimsJws(jwtToken)
                    .getBody();
        }

//        System.out.println(claims + "+++++++++");




        return new JsonResult<Orders>(OK);
    }

    private static Orders jsonToOrders(String strOrders){
        JSONObject jsonObject = JSON.parseObject(strOrders);
        Orders orders = new Orders();
        orders.setStartPoint((String) jsonObject.get("startPoint"));
        orders.setEndPoint((String) jsonObject.get("endPoint"));
        orders.setOrderType((String) jsonObject.get("orderType"));
        orders.setWeight((String) jsonObject.get("weight"));
        int i = Integer.parseInt(jsonObject.get("price").toString());
        orders.setPrice(i);
        orders.setOrderInfo((String) jsonObject.get("orderInfo"));
        orders.setSeveral(2);
        return orders;
    }

    @RequestMapping("get-ordersList")
    @ResponseBody
    public List<Orders> getordersList(int pages){
        System.out.println("nihao++++++++++");
        List<Orders> orders = new ArrayList<>();
        List<Object> orderList = redisUtils1.lRange("orderList", pages* 5L, pages* 5L +4);//从redis获取指定范围5个order
//        for (int i = 0; i < orderList.size(); i++) {
//            System.out.println(orderList.get(i));
//        }
        System.out.println(orderList.size());
        for (int i = 0; i < orderList.size(); i++) {
            orders.add((Orders) orderList.get(i));
//            System.out.println(orders1);
        }
//        orders = ordersService.getordersList(pages*5);
//        System.out.println(orders.toString()+"controller");
        return orders;
    }
}
