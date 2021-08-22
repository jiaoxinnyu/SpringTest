package com.xinyu.service;

import com.xinyu.mapper.model.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author jiaoxy
 */
public interface OrderService {
    /**
     * 插入订单数据信息 包含详情
     * @param flag
     */
    void insertOrder(boolean flag);

    /**
     * 测试方法 随机插入各种订单数据 不包含详情数据
     */
    void addOrder();

    /**
     * 根据订单ID查询订单信息
     * @param id
     * @return
     */
    Order selectOrderById(Long id);

    /**
     * 根据订单号查询订单信息
     * @param orderCode
     * @return
     */
    Order selectOrderByOrderId(String orderCode);

    /**
     * 关联查询 订单和详情
     * @param orderCode
     * @return
     */
    HashMap<String,Object> orderRelatedQuery(String orderCode);

    /**
     * 关联查询 订单和详情
     * 根据order表ID查询
     * @param id
     * @return
     */
    HashMap<String,Object> orderRelatedQueryById(Long id);

    /**
     * 修改订单信息
     * @param order
     * @return
     */
    int updateOrderById(Order order);

    /**
     * 跨库更新
     * @param code
     * @param flag
     */
    void crossLibraryUpdate(String code,boolean flag);
}
