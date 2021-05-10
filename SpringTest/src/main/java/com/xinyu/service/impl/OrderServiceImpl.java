package com.xinyu.service.impl;

import com.xinyu.mapper.OrderMapper;
import com.xinyu.mapper.model.Order;
import com.xinyu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiaoxy
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public void addOrder() {
        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            order.setOrderCode("orderCode:"+i);
            order.setAddress("广东省南山区深南大道");
            order.setAmount(100);
            order.setLength(1000);
            order.setWidth(50);
            order.setQuantity(50);
            orderMapper.insert(order);
        }
    }
}
