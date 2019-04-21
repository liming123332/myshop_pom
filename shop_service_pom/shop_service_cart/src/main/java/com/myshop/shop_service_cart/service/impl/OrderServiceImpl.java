package com.myshop.shop_service_cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myshop.entity.*;
import com.myshop.service.ICartService;
import com.myshop.service.IOrderService;
import com.myshop.shop_service_cart.dao.AddressMapper;
import com.myshop.shop_service_cart.dao.CartMapper;
import com.myshop.shop_service_cart.dao.OrderDetilsMapper;
import com.myshop.shop_service_cart.dao.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetilsMapper orderDetilsMapper;

    @Override
    public String insertOrders(int aid, User user) {
        Address address = addressMapper.selectById(aid);

        BigDecimal priceall = BigDecimal.valueOf(0.0);
        List<ShopCart> carts = cartService.getList(null, user);
        for (ShopCart shopCart : carts) {
            //累加
            priceall = priceall.add(shopCart.getAllprice());
        }
        //通过购物车信息封装订单和订单详情对象
        Orders orders = new Orders(
                0,
                UUID.randomUUID().toString(),
                address.getPerson(),
                address.getAddress(),
                address.getPhone(),
                address.getCode(),
                priceall,
                new Date(),
                0,
                user.getId(), null
        );
        ordersMapper.insert(orders);

        for (ShopCart cart : carts) {
            OrderDetils orderDetils=new OrderDetils();
            orderDetils.setGid(cart.getGid());
            orderDetils.setGimage(cart.getGood().getGimage());
            orderDetils.setGname(cart.getGood().getGname());
            orderDetils.setGnumber(cart.getGnumber());
            orderDetils.setGprice(cart.getGood().getGprice());
            orderDetils.setOid(orders.getId());
            orderDetilsMapper.insert(orderDetils);
        }

        cartService.deletCartByUid(user.getId());

        return orders.getOrderid();
    }

    @Override
    public List<Orders> getOrderList(int uid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uid",uid);
        List<Orders> ordersList=ordersMapper.getOrderList(uid);
        return ordersList;
    }

    @Override
    public Orders selectOrderByOrderid(String orderid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("orderid", orderid);
        Orders orders = ordersMapper.selectOne(queryWrapper);
        return orders;
    }

    @Override
    public void updateOrders(Orders orders) {
        ordersMapper.updateById(orders);
    }
}
