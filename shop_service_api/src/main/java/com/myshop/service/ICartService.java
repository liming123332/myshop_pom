package com.myshop.service;

import com.myshop.entity.ShopCart;
import com.myshop.entity.User;

import java.util.List;

public interface ICartService {

    public int addCart(String cartToken, ShopCart shopCart,User user);

    public List<ShopCart> getList(String cartToken,User user);

    public void mergeCarts(String cartToken,User user);

    void deletCartByUid(int id);
}
