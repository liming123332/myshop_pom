package com.myshop.shop_service_cart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myshop.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    List<Orders> getOrderList(int uid);
}
