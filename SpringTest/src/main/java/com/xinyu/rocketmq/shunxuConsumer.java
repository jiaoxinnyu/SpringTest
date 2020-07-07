package com.xinyu.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;



/**
 * 顺序消息消费，监听器一定要设置MessageListenerOrderly
 */
public class shunxuConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_3");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         * first 第一次启动是按照头部开始
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("TopicTest","*");
        //MessageListenerOrderly可以保证顺序 区别于MessageListenerConcurrently
        consumer.registerMessageListener(new MessageListenerOrderly() {

            Random random = new Random();

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                System.out.print(Thread.currentThread().getName() + " Receive New Messages: " );

                try {
                    //模拟业务逻辑处理中...
                    for (MessageExt msg: msgs)
                    {
                        System.out.println(msg + ", content:" + new String(msg.getBody()));
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    //如果处理失败了 返回一个状态，让他暂停一会再来继续处理这批消息
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }

            }
        });

        consumer.start();
        System.out.println("Consumer Started.");
    }
}