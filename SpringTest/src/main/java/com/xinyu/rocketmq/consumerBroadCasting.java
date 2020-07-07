package com.xinyu.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class consumerBroadCasting {
    public static void main(String[] args) throws MQClientException {
        final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_topic_producer");

        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("TopicTest","*");
        //改成广播模式 这样所有的机器都可以获取当前消费组的消息 默认是集群模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(list.size());
                System.out.println(list);
                //标记消息成功消费
                return  ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
