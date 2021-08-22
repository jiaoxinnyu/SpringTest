package com.xinyu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyu.mapper.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * @author jiaoxy
 */
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 订单多表关联查询，按照orderCode
     * @param orderCode
     * @return
     */
    @Select("select t1.order_code,t1.id,t1.address,t2.board_width,t2.board_code,t2.id as t2id from t_order_0 t1 " +
            "inner join t_order_details_0 t2 on t1.order_code = t2.order_code where t1.order_code = #{orderCode}")
    HashMap<String,Object> orderRelatedQuery(String orderCode);


    /**
     * 订单多表关联查询，按照orderId
     * @param id
     * @return
     */
    @Select("select t1.order_code,t1.id,t1.address,t2.board_width,t2.board_code,t2.id as t2id from t_order_0 t1 " +
            "inner join t_order_details_0 t2 on t1.order_code = t2.order_code where t1.id = #{id}")
    HashMap<String,Object> orderRelatedQueryByOrderId(Long id);

}
