package com.xinyu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServertSingel {
    public static void main(String[] args) throws IOException {
        //new socket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //bind port
        serverSocketChannel.bind(new InetSocketAddress(9090));
        //声明一个selector
        Selector selector = Selector.open();
        //注册服务端的监听事件到selector上面  本质上等于epoll.ctr函数添加一个文件描述符
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            //等同于内核的epoll wait函数 询问内核是否有事件产生
            while (selector.select(500)>0){
                //返回产生的事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext())
                {
                    //1.判断是否是链接事件 如果是的 那么新建立一个channel通道
                    // 注册到selector上面去 注意这里要注册的是读写事件不再是上面服务端注册的监听accept事件,设置为非阻塞
                    //2.如果是读写事件 那么我们应该开辟新的线程 从通道读取对应数据做业务处理，不要放在这里读取 会阻塞
                    // 这里我们只做监听事件，将事件丢入到内核里面去。
                    //3.删除key 防止重复遍历
                    SelectionKey next = selectionKeyIterator.next();
                    if (next.isAcceptable()){

                    }
                }

            }
        }

    }


}
