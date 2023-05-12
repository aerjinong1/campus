package com.example.campus.controller;

import com.example.campus.service.ex.ServiceException;
import com.example.campus.service.ex.*;
import com.example.campus.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int OK = 200;

    //请求处理方法，返回值为前端数据类型
    //自动将异常对象传递给此方法的参数列表上
    //项目中产生异常会被同意拦截到此方法中，这个方法就充当的是请求处理方法，返回值直接给到前端
    @ExceptionHandler(ServiceException.class)//用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof loginException) {
            result.setState(1001);
            result.setMessage("用户登录时密码错误");
        } else if (e instanceof loginException) {
            result.setState(1002);
            result.setMessage("用户不存在");
//        } else if (e instanceof UserNameNotFoundException) {
//            result.setState(5001);
//            result.setMessage("用户名不存在");
//        } else if (e instanceof PasswordNotMatchException) {
//            result.setState(5002);
//            result.setMessage("密码不正确");
//        } else if (e instanceof PasswordNotMatchException) {
//            result.setState(5003);
//            result.setMessage("更新数据产生未知异常");
        }
        return result;
    }

    /**
     * 获取session对象的uid
     *
     * @param session
     * @return 当前登录用户uid的值
     */
    protected final HttpSession getJWTFromSession(HttpSession session) {
//        HttpServletRequest request=ServletActionContext.getRequest();
//        HttpSession sessions= request.getSession();
//        return Integer.valueOf(session.getAttribute("campus").toString());
        return session;
    }

    /**
     * 获取当前登录用户的username
     *
     * @param session
     * @return 当前登录用户的username
     */

}
