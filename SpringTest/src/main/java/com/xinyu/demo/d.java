package com.xinyu.demo;

import com.xinyu.Controller.test;
import org.apache.kafka.common.utils.CopyOnWriteMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class d {
    public void d(){
        System.out.println("d()");
    }
    private int i=0;
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8090));
        serverSocketChannel.configureBlocking(false);
        FileOutputStream fileOutputStream = new FileOutputStream("");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("str".getBytes());
        byteBuffer.flip();

        fileOutputStreamChannel.write(byteBuffer);

        System.out.println(Runtime.getRuntime().availableProcessors());
        HashMap hashMap =new HashMap();
        hashMap.put("","");
        Collections.synchronizedMap(hashMap);
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(1);
        CopyOnWriteMap<String,String> copyOnWriteMap = new CopyOnWriteMap();
        copyOnWriteMap.put("1","1");
       // new d().test();
    }

    public void test(){
        System.out.println(i);
        i++;
        int a,c,d,e,f;
        Byte [] b =new Byte[512];
        test();
    }
}
