package com.myshop.shop_service_cart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myshop.entity.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
    int addAddress(Address address);
}
