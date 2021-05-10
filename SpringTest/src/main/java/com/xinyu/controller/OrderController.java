package com.xinyu.controller;

import com.xinyu.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiaoxy
 */
@RequestMapping("/order")
@RestController
@Api(tags = "订单管理")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "添加订单",notes = "该方法为测试方法不需要入参")
    @RequestMapping(value = "/addOrder",method = RequestMethod.GET)
    public void addOrder(){
        orderService.addOrder();
    }

}
