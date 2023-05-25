package com.example.campus.entity;

import lombok.Data;

@Data
public class Address {
    private long XValue;
    private long YValue;
    private String name;




    void Address(){

    }
    public Address(long xvalue,long yvalue,String name){
        this.XValue=xvalue;
        this.YValue=yvalue;
        this.name=name;
    }
}
