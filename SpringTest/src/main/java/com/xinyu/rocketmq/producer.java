package com.xinyu.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;

public class producer {
    public static void main(String[] args) throws Exception {
        final DefaultMQProducer Producer = new DefaultMQProducer("test_topic_producer");
        //
        Producer.setNamesrvAddr("127.0.0.1:9876");
        Producer.start();
        Producer.setSendLatencyFaultEnable(true);
        Message message = new Message("TopicTest", "tagsA", "test".getBytes());
        try {
            //同步发送
            SendResult sendResult = Producer.send(message);
            //发送事务消息 事务消息发送失败则 直接进行回滚
            TransactionProducer transactionProducer = new TransactionProducer();
            TransactionSendResult transactionSendResult = transactionProducer.getProducer().sendMessageInTransaction(message,null);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
