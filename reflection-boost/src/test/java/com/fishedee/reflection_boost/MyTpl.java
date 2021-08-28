package com.fishedee.reflection_boost;

import java.util.List;
import java.util.Map;

public class MyTpl<T1, T2 extends Nothing> {

    public Map<T1,List<T2>> data;

    public void go(T1 a){

    }

    public void go2(T2 a, List<T2> b){

    }
}
