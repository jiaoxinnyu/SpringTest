package com.xinyu.demo;

public class c extends a {
    public static  int i=4;

    static {
        System.out.println(i);
    }

    private int j = 6;

    c(){
        System.out.println(j);
    }
    public static void main(String[] args) {
        new c();
        System.out.println("start");
    }

}
