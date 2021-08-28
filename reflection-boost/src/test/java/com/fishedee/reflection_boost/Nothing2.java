package com.fishedee.reflection_boost;

public class Nothing2 extends Nothing{
    private String mm;

    public void setMm(String mm){
        this.mm = mm;
    }

    public String getMm(){
        return this.mm;
    }

    public String toString(){
        return "{mm "+this.mm+"}";
    }
}
