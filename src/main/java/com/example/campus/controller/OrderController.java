package com.example.campus.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.campus.entity.Orders;
import com.example.campus.entity.User;
import com.example.campus.service.impl.OrdersServiceImpl;
import com.example.campus.service.impl.UserServiceImpl;
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
import java.util.Arrays;
import java.util.List;


@Controller
public class OrderController extends BaseController {
    @Autowired
    @Qualifier("redisUtils1")
    private RedisUtils1 redisUtils1;

    @Autowired
    private UserServiceImpl userService;
    public static final int OK = 200;

    @Value("${jwt.secretKey}")
    private String secrets;

    @Autowired
    private OrdersServiceImpl ordersService;

    public User getUserFromJWT(HttpSession session) {
        String jwtToken = (String) session.getAttribute("jwtToken");
        Claims claims = Jwts
                .parser()
                .setSigningKey(secrets)
                .parseClaimsJws(jwtToken)
                .getBody();
        System.out.println(claims + "+++++++++");
        int userId = (Integer) claims.get("id");
        User user = userService.getUserInfo(userId);


        return user;
    }

    @RequestMapping("getOrdersByOrderId")
    @ResponseBody
    public JsonResult<Orders> getOrdersByOrderId(int ordersId) {
        Orders orders = (Orders) redisUtils1.get("order:" + ordersId);

        if (orders == null) {
            Orders res = ordersService.getOrdersByOrderId(ordersId);
            System.out.println("redis中没有订单，前往数据库查找");
            redisUtils1.set("order:" + ordersId, res);

            return new JsonResult<Orders>(OK, orders);
        } else {
            System.out.println("以从redis查询到");
            return new JsonResult<Orders>(OK, orders);
        }


    }

    @RequestMapping("createOrders")
    @ResponseBody
    public JsonResult<Orders> createOrders(HttpSession session, @RequestBody String orders) {


        JSONObject jsonObject = JSON.parseObject(orders);
        Orders orders1 = jsonToOrders(orders);
        ordersService.createOrders(orders1);
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null) {
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(secrets)
                    .parseClaimsJws(jwtToken)
                    .getBody();
        }

//        System.out.println(claims + "+++++++++");


        return new JsonResult<Orders>(OK);
    }

    private static Orders jsonToOrders(String strOrders) {
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
    public List<Orders> getordersList(int pages) {
        System.out.println("nihao++++++++++");
        List<Orders> orders = new ArrayList<>();
        List<Object> orderList = redisUtils1.lRange("orderList", pages * 5L, pages * 5L + 4);//从redis获取指定范围5个order
//        for (int i = 0; i < orderList.size(); i++) {
//            System.out.println(orderList.get(i));
//        }
        System.out.println(orderList.size()+"orderList.size");
        for (int i = 0; i < orderList.size(); i++) {
            orders.add((Orders) orderList.get(i));
//            System.out.println(orders1);
        }
//        orders = ordersService.getordersList(pages*5);
//        System.out.println(orders.toString()+"controller");
        return orders;
    }

    @RequestMapping("acceptOrder")
    @ResponseBody
    public void acceptOrder(int ordersId, HttpSession session) {
        User user = getUserFromJWT(session);
        redisUtils1.remove("order:" + ordersId);
        ordersService.acceptOrder(ordersId, user.getUserId());
        Orders res = ordersService.getOrdersByOrderId(ordersId);
        redisUtils1.set("order:" + ordersId, res);
    }

    @RequestMapping("getMyOrders")
    @ResponseBody
    public List<Orders> getMyOrders(int pages, HttpSession session) {
        User user = getUserFromJWT(session);
        List<Orders> orders = new ArrayList<>();
        List<Object> orderList = redisUtils1.lRange("orderList:user:" + user.getUserId(), pages * 5L, pages * 5L + 4);//从redis获取指定范围5个order
        System.out.println(user.getUserId());
        System.out.println(orderList+"toString");
        System.out.println(orderList.size()+"orderlist.size");
        if (!redisUtils1.exists("orderList:user:" + user.getUserId())) {

            orders = ordersService.getOrdersListByUserId(user.getUserId());
            System.out.println("redis没有数据，从sql查找");

        }
//        for (int i = 0; i < orderList.size(); i++) {
//            System.out.println(orderList.get(i));
//        }
//        System.out.println(orderList.size());
        for (int i = 0; i < orderList.size(); i++) {
            orders.add((Orders) orderList.get(i));

//            System.out.println(orders1);
        }
//        orders = ordersService.getordersList(pages*5);
//        System.out.println(orders.toString()+"controller");
        return orders;
    }
}
