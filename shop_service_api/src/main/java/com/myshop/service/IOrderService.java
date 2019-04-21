package com.myshop.service;

import com.myshop.entity.Orders;
import com.myshop.entity.User;


import java.util.List;

public interface IOrderService {
    String insertOrders(int aid, User user);

    List<Orders> getOrderList(int id);

    Orders selectOrderByOrderid(String orderid);

    void updateOrders(Orders orders);
}
