package com.myshop.shop_service_cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myshop.entity.Address;
import com.myshop.service.IAddressService;
import com.myshop.shop_service_cart.dao.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> getAddressByUid(int uid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uid",uid);
        List addressList = addressMapper.selectList(queryWrapper);
        return addressList;
    }

    @Override
    public int addAddress(Address address) {
        int i = addressMapper.addAddress(address);
        return i;
    }
}
