package com.xinyu.controller;

import com.xinyu.redispackage.RedisLock;
import com.xinyu.service.OrderService;
import com.xinyu.utils.ThreadPoolUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiaoxy
 */
@RequestMapping("/redisson_test")
@RestController
@Api(tags = "Redisson分布式锁测试")
public class RedissonController {
    @Autowired
    private RedisLock redisLock;

    @ApiOperation(value = "添加分布式锁",notes = "该方法为测试方法不需要入参")
    @RequestMapping(value = "/lock",method = RequestMethod.GET)
    public void addRedissonLock(){
        for (int i = 0; i < 10; i++) {
            ThreadPoolUtils.getInstance().execute(()-> {
                for (int j = 0; j < 10; j++) {
                    redisLock.redissonTest("order_id"+j);
                }
            });
        }


    }
}
