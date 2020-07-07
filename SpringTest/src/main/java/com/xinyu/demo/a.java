package com.xinyu.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;



public class a {
    public static  int i=3;

    static {
        System.out.println(i);
    }

    private int j = 5;

    a(){
        System.out.println(j);
    }

    public static void main(String[] args) {

    }
}
