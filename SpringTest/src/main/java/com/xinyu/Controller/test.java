package com.xinyu.Controller;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class test {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<>();
        stringStringConcurrentHashMap.put("1","2");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put(null,null);
        stringStringHashMap.put("2",null);
        System.out.println(stringStringHashMap.get(2));
        LinkedHashMap<String, String> gLinkedHashMap = new LinkedHashMap<>();
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        linkedBlockingQueue.put("1");
        System.out.println(linkedBlockingQueue.take());
        synchronousQueue.put("1");
        synchronousQueue.put("2");
        System.out.println(synchronousQueue.take());

    }
}
