package com.example.campus;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

//@PropertySource("classpath:application.yml")
public class test {
    @Value("${jwt.secretKey}")
    private String secret;

    @Test
    public void generateToken() {

        // JWT头部分信息【Header】
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // 载核【Payload】
        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", "1234567890");
        payload.put("name", "John Doe");
        payload.put("admin", true);

        // 声明Token失效时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 300);// 300s

        // 生成Token
        String token = Jwts.builder()
                .setHeader(header)// 设置Header
                .setClaims(payload) // 设置载核
                .setExpiration(instance.getTime())// 设置生效时间
                .signWith(SignatureAlgorithm.HS256, "bsxg") // 签名,这里采用私钥进行签名,不要泄露了自己的私钥信息
                .compact(); // 压缩生成xxx.xxx.xxx

        System.out.println(token);
    }

    @Test
    public void getInfoByJwt() {
        // 生成的token
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImV4cCI6MTY4MTgxMTUwM30.WSyfyNNacXiyzNTc2Zmn-linreb6iKyGWFh1Ovi4x8U";
        // 解析head信息
        JwsHeader jwsHeader = Jwts
                .parser()
                .setSigningKey("bsxg")
                .parseClaimsJws(token)
                .getHeader();

        System.out.println(jwsHeader); // {typ=JWT, alg=HS256}
        System.out.println("typ:" + jwsHeader.get("typ"));

        // 解析Payload
        Claims claims = Jwts
                .parser()
                .setSigningKey("bsxg")
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims);// {sub=1234567890, name=John Doe, admin=true, exp=1663297431}
        System.out.println("sub:" + claims.get("sub"));

        // 解析Signature
        String signature = Jwts
                .parser()
                .setSigningKey("bsxg")
                .parseClaimsJws(token)
                .getSignature();
        System.out.println(signature); // Ju5EzKBpUnuIRhDG1SU0NwMGsd9Jl_8YBcMM6PB2C20
    }

    @Test
    public void jsonSwitchString(){
        String st = "{\"startPoint\":\"ewr\",\"endPoint\":\"wf\",\"orderType\":\"外卖\",\"weight\":\"1到3kg\",\"price\":\"1到3kg\",\"orderInfo\":\"re\"}";
        JSONObject jsonObject = JSON.parseObject(st);
//        System.out.println(jsonObject.get("startPoint"));
        System.out.println(jsonObject.toString());

    }

    @Test public void sss(){
        System.out.println(new Date());
    }
}
