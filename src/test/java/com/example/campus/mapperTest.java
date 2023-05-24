package com.example.campus;

import com.example.campus.mapper.OrderMapper;
import com.example.campus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.TimeZone;

@SpringBootTest
public class mapperTest {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private OrderMapper orderMapper;
    @Test
    public void getall(){
        userMapper.getall();
    }
    @Test
    public void signup(){
        userMapper.signup("wxz","123");

    }
    @Test
    public void getinfo(){
        userMapper.getUserInfo(1223);
    }

    @Test
    public void getaddress(){
        System.out.println(userMapper.getUserAddress(1223));
    }
    @Test
    public void addaddress(){
        System.out.println(userMapper.addUserAddress(1223,"天津市津南区S113(外环南路)海天南苑13号楼13号楼5楼502见党旗"));
    }
//    @Test
//    public void orderGetall(){
//        System.out.println(orderMapper.getordersList(1,2));
//    }
    @Test
    public void getOrderByid(){

        System.out.println(orderMapper.getOrdersByOrderId(12));
    }
    @Test
    public void createOrders(){
        System.out.println("原时间："+new Date());
        TimeZone timeZone=TimeZone.getTimeZone("ETC?GMT-8");
        TimeZone.setDefault(timeZone);
        System.out.println("修改后："+new Date());
        orderMapper.createOrders("tjs","jnq",12,"redisOfTask",new Date(),2);
    }

    @Test
    public void getordersList(){
        System.out.println(orderMapper.getordersList(2,1)+"123");
    }
}
