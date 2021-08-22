package com.xinyu.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.xinyu.mapper.model.Order;
import com.xinyu.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author jiaoxy
 */
@RequestMapping("/order")
@RestController
@Api(tags = "订单管理")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "添加订单和详情 跨库事物", notes = "测试跨库事物功能是否正常 flag-是否让详情表报错 true-是 false-否")
    @RequestMapping(value = "/insertOrderAndDetails", method = RequestMethod.GET)
    public R insertOrderAndDetails(boolean flag) {
        try {
            orderService.insertOrder(flag);
            return R.ok("添加成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.failed(e.getMessage());
        }
    }

    @ApiOperation(value = "添加订单", notes = "该方法为测试方法不需要入参")
    @RequestMapping(value = "/addOrder", method = RequestMethod.GET)
    public void addOrder() {
        orderService.addOrder();
    }

    @ApiOperation(value = "根据订单ID查询订单", notes = "入参订单ID-》 主键ID")
    @RequestMapping(value = "selectOrderByOrderId", method = RequestMethod.GET)
    public R selectOrderByOrderId(Long orderId) {
        Order order = orderService.selectOrderById(orderId);
        return R.ok(order);
    }

    @ApiOperation(value = "根据订单号码查询订单", notes = "入参订单编号")
    @RequestMapping(value = "selectOrderByOrderCode", method = RequestMethod.GET)
    public R selectOrderByOrderCode(String orderCode) {
        return R.ok(orderService.selectOrderByOrderId(orderCode));
    }

    @ApiOperation(value = "根据订单ID修改订单属性", notes = "入参主键ID")
    @RequestMapping(value = "updateOrderById", method = RequestMethod.POST)
    public R updateOrderById(@RequestBody Order order){
        int i = orderService.updateOrderById(order);
        R<Order> orderR = new R<>();
        orderR.setMsg("修改影响行数："+i);
        orderR.setCode(200);
        return orderR;
    }

    /**
     * shardingsphere 不支持跨库关联查询
     * @param orderCode
     * @return
     */
    @ApiOperation(value = "根据订单号码关联查询订单详情", notes = "关联查询订单和订单详情信息")
    @RequestMapping(value = "selectOrderDetailsByOrderCode", method = RequestMethod.GET)
    public R selectOrderDetailsByOrderCode(String orderCode) {
        return R.ok(orderService.orderRelatedQuery(orderCode));
    }
    /**
     * shardingsphere 不支持跨库关联查询
     * @param orderId
     * @return
     */
    @ApiOperation(value = "根据订单ID关联查询订单详情", notes = "关联查询订单和订单详情信息")
    @RequestMapping(value = "selectOrderDetailsByOrderId", method = RequestMethod.GET)
    public R selectOrderDetailsByOrderId(Long orderId) {
        HashMap<String, Object> res = orderService.orderRelatedQueryById(orderId);
        log.info(res.toString());
        return R.ok(res);
    }


    @ApiOperation(value = "根据订单编号【跨库修改】订单属性和订单详情表", notes = "验证执行跨库更新后代码抛出异常是否能够回滚")
    @RequestMapping(value = "crossLibraryUpdateOrder", method = RequestMethod.POST)
    public R updateOrderByCode(String code,boolean flag){
        try {
            orderService.crossLibraryUpdate(code, flag);
            return R.ok("跨库修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed(e.getMessage());
        }
    }



}
