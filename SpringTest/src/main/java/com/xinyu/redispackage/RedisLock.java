package com.xinyu.redispackage;

import com.xinyu.config.ConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author jiaoxy
 */
@Slf4j
@Service
public class RedisLock {

    @Autowired
    RedissonClient redissonClient;
    /**
     * 测试redisson锁
     * @param order_id
     * @return
     */
    public boolean redissonTest(String order_id){
        String key = ConfigConstant.REDIS_LOCK +order_id;
        RLock lock = redissonClient.getLock(key);
        try {
            //设置超时时间
            lock.lock(500, TimeUnit.MILLISECONDS);
            log.info("加锁成功:"+order_id);
            Thread.sleep(300);
            return true;
        }catch (Exception e){
            log.error("异常",e);
            return false;
        }finally {
            lock.unlock();
            log.info("解锁成功："+order_id);
        }
    }

}
