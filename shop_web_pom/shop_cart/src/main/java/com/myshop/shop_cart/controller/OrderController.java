package com.myshop.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.myshop.aop.IsLogin;
import com.myshop.entity.Address;
import com.myshop.entity.Orders;
import com.myshop.entity.ShopCart;
import com.myshop.entity.User;
import com.myshop.service.IAddressService;
import com.myshop.service.ICartService;
import com.myshop.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private ICartService cartService;

    @Reference
    private IOrderService orderService;

    @Reference
    private IAddressService addressService;

    @IsLogin(mustLogin = true)
    @GetMapping("/edit")
    public String addOrder(User user, Model model){
        List<ShopCart> carts=cartService.getList(null,user);
        BigDecimal priceall = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : carts) {
            priceall = priceall.add(shopCart.getAllprice());
        }
        List<Address> addresses = addressService.getAddressByUid(user.getId());

        model.addAttribute("priceall",priceall);
        model.addAttribute("carts",carts);
        model.addAttribute("addresses",addresses);
        return "rightAddress";
    }

    @IsLogin
    @RequestMapping("/insertOrders")
    @ResponseBody
    public String insertOrders(int aid,User user){
        //System.out.println(aid);
        String orderid = orderService.insertOrders(aid, user);
        return orderid;
    }

    @IsLogin(mustLogin = true)
    @RequestMapping("/orderList")
    public String orderList(User user,Model model){
        List<Orders> orderList = orderService.getOrderList(user.getId());
        model.addAttribute("orderList",orderList);
        return "orderList";
    }


}
