package com.xinyu.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

public class TransactionProducer {

    private String producerGroup = "trac_producer_group";

    //事务监听器接口 用于接受rocket mq回调
    private TransactionListener transactionListener = new TransactionListenerImpl();

    private TransactionMQProducer producer = null;

    // 一般自定义线程池的 这个线程池里面的线程是用来处理rocketmq回调你的请求的
    private ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        }

    });


    public TransactionProducer() {
        producer = new TransactionMQProducer(producerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr("192.168.111.132:9876");
        //设置事务消息生产者设置对应的回调函数
        producer.setTransactionListener(transactionListener);
        //设置对应的线程池
        producer.setExecutorService(executorService);
        //启动事务消息生产者
        start();
    }

    public TransactionMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown() {
        this.producer.shutdown();
    }

}





/**
 * 实际处理业务的类，可能是本地带事务性的方法中处理 在这个之前先发送一条hift消息给broker端
 * 如果发送失败直接进行本地事务回滚操作，因为可能连broker都连不上了 或者没法用了
 *  producer.sendMessageInTransaction()
 *
 * @author asus
 */
class TransactionListenerImpl implements TransactionListener{
    /**
     * hift消息发送成功了 回调这个接口
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {


        String body = new String(msg.getBody());
        String key = msg.getKeys();
        String transactionId = msg.getTransactionId();
        System.out.println("transactionId="+transactionId+", key="+key+", body="+body);
        // 执行本地事务begin 写自己的业务代码逻辑
        //根据自己的代码逻辑去选择 提交 回滚 或者不做操作
        // COMMIT_MESSAGE ROLLBACK_MESSAGE UNKNOW

        try {
            return LocalTransactionState.COMMIT_MESSAGE;
        }catch (Exception e){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }


    }

    /**
     * 因为各种原因我们没有给rocketmq返回commit 或者roback
     * 则broker会对我们进行回查
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        //超时回查方法 超时提交或提交Unknow状态则会触发broker的事务回查
        //默认回查15次 6s后第一次回查后续间隔60s一次
        String body = new String(msg.getBody());
        String key = msg.getKeys();
        //检查本地事务状态
        String transactionId = msg.getTransactionId();
        System.out.println("transactionId="+transactionId+", key="+key+", body="+body);
        Integer integer = Integer.valueOf(transactionId);
        if(null != integer){
        switch (integer)
        {
            case 0:return LocalTransactionState.UNKNOW;
            case 1:return LocalTransactionState.COMMIT_MESSAGE;
            case 2:return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }

}
