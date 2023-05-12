package com.example.campus.controller;

import com.example.campus.entity.User;
import com.example.campus.service.impl.UserServiceImpl;
import com.example.campus.util.JsonResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.*;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

;

//@RequestMapping("user")
@Controller
public class UserController extends BaseController {
    public static final int OK = 200;
    @Value("${jwt.secretKey}")
    private String secrets;

    @Autowired
    private UserServiceImpl userService;

    public List<User> getall() {

        return userService.getall();
    }

    @RequestMapping("signup")
    public JsonResult<Void> signup(String userName, String password) {
//        System.out.println(userService.signup(userName, password));
        return new JsonResult<>(OK);
    }

    @RequestMapping("login")
    @ResponseBody
    public JsonResult<User> login(String userName, String password, HttpSession session) {
        User res = userService.login(userName, password);
        Object jwtToken = session.getAttribute("jwtToken");
        if (jwtToken != null) {
//            System.out.println(jwtToken.toString()+"==================+++++++++=========+++==========++++=========");

        }
        //controller层接收数据，生成token，并响应
        // JWT头部分信息【Header】
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // 载核【Payload】
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", res.getUserId());
        payload.put("name", "John Doe");
        payload.put("admin", true);

        // 声明Token失效时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 3000);// 300s

        // 生成Token
        String token = Jwts.builder()
                .setHeader(header)// 设置Header
                .setClaims(payload) // 设置载核
                .setExpiration(instance.getTime())// 设置生效时间
                .signWith(SignatureAlgorithm.HS256, secrets) // 签名,这里采用私钥进行签名,不要泄露了自己的私钥信息
                .compact(); // 压缩生成xxx.xxx.xxx

        System.out.println(token);

        session.setAttribute("jwtToken", token);

        return new JsonResult<User>(OK, res);


    }

    @RequestMapping("getUserInfo")
    @ResponseBody
    public JsonResult<User> getUserInfo(HttpSession session) {
        String jwtToken = (String) session.getAttribute("jwtToken");
        Claims claims = Jwts
                .parser()
                .setSigningKey(secrets)
                .parseClaimsJws(jwtToken)
                .getBody();
        System.out.println(claims + "+++++++++");
        int userId = (Integer) claims.get("id");
        User res = userService.getUserInfo(userId);


        return new JsonResult<User>(OK, res);
    }

    @RequestMapping("upload")
    public String upload(MultipartFile file,HttpSession session){
        System.out.println("upload");
        String jwtToken = (String) session.getAttribute("jwtToken");
        Claims claims = Jwts
                .parser()
                .setSigningKey(secrets)
                .parseClaimsJws(jwtToken)
                .getBody();
        System.out.println(claims + "+++++++++");
        int userId = (Integer) claims.get("id");



        if (file.isEmpty()){
            return "null";
        }
        String originalFilename = file.getOriginalFilename();

        String ext = "."+originalFilename.split("\\.")[1];
        String uuid = UUID.randomUUID().toString().replace("-","");
        String filename = uuid+ext;
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String path = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()+"/src/main/resources/static/pages/img";

        String img = "./img/"+filename;
        String newpath = path+"/"+filename;
        System.out.println(newpath);

        try{
            file.transferTo(new File(newpath));
        }catch (IOException e){
            e.printStackTrace();
        }
        userService.setImg(img, userId);
        return "http://localhost:8080/pages/accountInfo.html";
    }
    @RequestMapping("addaddress")
    public int addaddress(int userid,String address){
        Integer integer = userService.addAddress(userid, address);
        return integer;
    }
    @RequestMapping("getUser-address")
    @ResponseBody
    public JsonResult<User> getUseraddress(HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        System.out.println(jwtToken);
        Claims claims = Jwts
                .parser()
                .setSigningKey(secrets)
                .parseClaimsJws(jwtToken)
                .getBody();
        System.out.println(claims + "+++++++++");
        int userId = (Integer) claims.get("id");
        String address = userService.getAddress(userId);
        User user = new User();
        user.setAddress(address);
        return new JsonResult<User>(OK,user);
    }





}
