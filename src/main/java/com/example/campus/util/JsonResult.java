package com.example.campus.util;

import java.io.Serializable;

public class JsonResult<E> implements Serializable {
    //状态码
    private Integer state;
    //描述信息
    private String  message;
    //数据
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public JsonResult(Throwable e) {
        this.message=e.getMessage();
    }
}
