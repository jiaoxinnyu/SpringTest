package com.xinyu.mapper.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jiaoxy
 * @TableName 必须设置为逻辑表名称
 */
@Data
@TableName(value = "t_order_0")
@ApiModel(value="Order对象", description="订单表")
public class Order implements Serializable {
    private Long id;
    private String orderCode;
    private Integer width;
    private Integer length;
    private Integer amount;
    private Integer quantity;
    private String address;

}
