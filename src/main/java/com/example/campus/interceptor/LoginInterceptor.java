package com.example.campus.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("jwtToken");
        if (obj == null) {//用户未登录
            response.sendRedirect("http://localhost:8080/pages/login.html");
            return false;
        }
        System.out.println("++++++++++++"+obj.toString());
        return true;
    }
}
