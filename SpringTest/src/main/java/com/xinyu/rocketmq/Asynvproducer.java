package com.xinyu.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class Asynvproducer {
    public static void main(String[] args) throws Exception {
        final DefaultMQProducer Producer = new DefaultMQProducer("test_topic_producer");
        //
        Producer.setNamesrvAddr("127.0.0.1:9876");
        Producer.start();
        //设置重试次数
        Producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i <10 ; i++) {
            new Thread(){
                @Override
                public void run() {
                    while (true){
                        //消息主题，消息tag 可以做标示，消息内容
                        Message message = new Message("TopicTest", "tagsA", "test".getBytes());
                        message.putUserProperty("a","10");
                        message.putUserProperty("b","20");
                        message.setDelayTimeLevel(3);//延迟消息级别
                        try {
                              //异步发送
                              Producer.send(message, new SendCallback() {
                                  @Override
                                  public void onSuccess(SendResult sendResult) {

                                  }

                                  @Override
                                  public void onException(Throwable throwable) {

                                  }
                              });
                              //发送单向消息到mq 你不关心发送有没有成功 也不需要返回结果 发送后就不管了
                              Producer.sendOneway(message);
                              //根据自己的队列选择器发送顺序消息 顺序发送消息必须采用同步模式发送 arg参数就是区分id
                              Producer.send(message, new IDHashMessageQueueSelector(),1111111);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }.start();

        }
    }
}

/**
 * 一种是采用所有的消息都用同一批key来取模，
 * 另外一种这里直接返回固定queue 比如 get（0）
 * mqs：该topic下面所有可选的messagequeue
 * msg：待发送的消息
 * arg：发送消息时候传递的参数 根据该参数做区分
 */
class IDHashMessageQueueSelector implements MessageQueueSelector {
    @Override
    public MessageQueue select(List<MessageQueue> mqs, Message msg,
                               Object arg) {
        int id = Integer.parseInt(arg.toString());
        int size = mqs.size();
        int index = id%size;
        return mqs.get(index);
    }



}

