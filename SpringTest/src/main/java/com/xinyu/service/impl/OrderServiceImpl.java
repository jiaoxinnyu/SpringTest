package com.xinyu.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xinyu.mapper.OrderDetailsMapper;
import com.xinyu.mapper.OrderMapper;
import com.xinyu.mapper.model.Order;
import com.xinyu.mapper.model.OrderDetails;
import com.xinyu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

/**
 * @author jiaoxy
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderDetailsMapper orderDetailsMapper;

    /**
     * TransactionType.LOCAL 本地事务 不具备分布式特性
     * @param flag
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.LOCAL)
    public void insertOrder(boolean flag) {
        String uid = UUID.randomUUID().toString();
        Order order = new Order();
        order.setOrderCode(uid);
        order.setAddress("广东省南山区深南大道");
        order.setAmount(100);
        order.setLength(1000);
        order.setWidth(50);
        order.setQuantity(50);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setBoardCode("A2CE");
        orderDetails.setBoardLength(1000);
        orderDetails.setBoardWidth(100);
        orderDetails.setNetArea("100m");
        orderDetails.setOrderCode(uid);
        //故意注释掉这个 让insert详情发生异常
        if(!flag){
            orderDetails.setOrderType(2);
        }
        orderDetails.setStatus(0);
        int insertOrderCount = orderMapper.insert(order);
        log.info("============order表数据插入成功 影响行数："+insertOrderCount);
        int detailsCount = orderDetailsMapper.insert(orderDetails);
        log.info("============orderDetails表数据插入成功 影响行数："+detailsCount);
    }

    @Override
    public void addOrder() {
        for (int i = 0; i < 20000; i++) {
            Order order = new Order();
            order.setOrderCode(UUID.randomUUID().toString());
            order.setAddress("广东省南山区深南大道");
            order.setAmount(100);
            order.setLength(1000);
            order.setWidth(50);
            order.setQuantity(50);
            orderMapper.insert(order);
        }
    }

    @Override
    public Order selectOrderById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public Order selectOrderByOrderId(String orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.lambda().eq(Order::getOrderCode,orderId);
        return orderMapper.selectOne(orderQueryWrapper);
    }

    @Override
    public HashMap<String, Object> orderRelatedQuery(String orderCode) {
        return orderMapper.orderRelatedQuery(orderCode);
    }

    @Override
    public HashMap<String, Object> orderRelatedQueryById(Long id) {
        return orderMapper.orderRelatedQueryByOrderId(id);
    }

    @Override
    public int updateOrderById(Order order) {
        return orderMapper.updateById(order);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.LOCAL)
    public void crossLibraryUpdate(String code,boolean flag) {
        Order order = new Order();
        order.setAddress("跨库更新"+new Date());
        order.setAmount(88);
        order.setLength(88);
        order.setWidth(88);
        order.setQuantity(88);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setBoardCode("跨库更新");
        orderDetails.setBoardLength(88);
        orderDetails.setBoardWidth(88);
        orderDetails.setNetArea("跨库更新");
        orderMapper.update(order,new UpdateWrapper<Order>().lambda().eq(Order::getOrderCode,code));
        orderDetailsMapper.update(orderDetails,new UpdateWrapper<OrderDetails>().lambda().eq(OrderDetails::getOrderCode,code));
        log.info("=============跨库更新完毕=============");
        if(flag){
            throw new NullPointerException();
        }
    }
}
