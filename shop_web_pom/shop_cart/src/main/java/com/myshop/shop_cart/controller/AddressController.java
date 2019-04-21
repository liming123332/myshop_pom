package com.myshop.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.myshop.aop.IsLogin;
import com.myshop.entity.Address;
import com.myshop.entity.ShopCart;
import com.myshop.entity.User;
import com.myshop.service.IAddressService;
import com.myshop.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {


    @Reference
    private IAddressService addressService;


    @GetMapping("/addAddress")
    @ResponseBody
    @IsLogin(mustLogin = true)
    public String addAddress(Address address,User user){
        address.setUid(user.getId());
        //System.out.println(address);
        addressService.addAddress(address);
        return "success";
    }
}
