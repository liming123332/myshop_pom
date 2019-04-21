package com.myshop.service;

import com.myshop.entity.Address;

import java.util.List;

public interface IAddressService {
    public List<Address> getAddressByUid(int uid);
    public int addAddress(Address address);
}
