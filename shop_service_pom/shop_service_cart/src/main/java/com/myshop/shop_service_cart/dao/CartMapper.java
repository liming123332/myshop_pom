package com.myshop.shop_service_cart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myshop.entity.ShopCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper extends BaseMapper<ShopCart> {
}
