package com.example.campus;

import com.alibaba.fastjson2.JSON;
import com.example.campus.controller.UserController;
import com.example.campus.controller.OrderController;
import com.example.campus.entity.Orders;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import java.util.Date;
import java.util.Enumeration;

@SpringBootTest
public class controllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private OrderController orderController;
    @Test
    public void getall(){
        userController.getall();
    }
    @Test
    public void signup(){
        userController.signup("www","123");
    }
//    @Test void login(){
//        userController.login("www","123");
//    }
    @Test void getInfo(){
//        userController.getInfo("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImV4cCI6MTY4MTI5NDMwM30.GFSY0RYtWVryRPE4LW-gsVNtMMr7A3wms4G1DPVf_IQ");
    }

    @Test
    public void addaddress(){
        System.out.println(userController.addaddress(1223, "newaddress"));
    }
    @Test
    public void getaddress(){
    userController.getUseraddress(new MockHttpSession());
    }
    @Test
    public void getOrdersByOrderId(){
        System.out.println(orderController.getOrdersByOrderId(12).getData());
    }
    @Test
    public void createOrders(){
        Orders orders =new Orders();
        orders.setStartPoint("asdf");
        orders.setEndPoint("wer");
        orders.setPrice(32123);
        orders.setOrderInfo("23w");
        orders.setCreateTime(new Date());
        orders.setSeveral(333);
        System.out.println(orders.toString());
        orderController.createOrders(
                new HttpSession() {
            @Override
            public long getCreationTime() {
                return 0;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public long getLastAccessedTime() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public void setMaxInactiveInterval(int i) {

            }

            @Override
            public int getMaxInactiveInterval() {
                return 0;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public void invalidate() {

            }

            @Override
            public boolean isNew() {
                return false;
            }
        }, JSON.toJSONString(orders)        );

    }


    @Test
    public void getorderList(){
        System.out.println(orderController.getordersList(0));
    }
    @Test
    public void acceptOrders(){
        orderController.acceptOrder(18, new HttpSession() {
            @Override
            public long getCreationTime() {
                return 0;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public long getLastAccessedTime() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public void setMaxInactiveInterval(int i) {

            }

            @Override
            public int getMaxInactiveInterval() {
                return 0;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public void invalidate() {

            }

            @Override
            public boolean isNew() {
                return false;
            }
        });
    }
}
