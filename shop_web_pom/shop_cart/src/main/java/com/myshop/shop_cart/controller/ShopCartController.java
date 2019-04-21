package com.myshop.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.myshop.aop.IsLogin;
import com.myshop.entity.ShopCart;
import com.myshop.entity.User;
import com.myshop.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class ShopCartController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private ICartService cartService;

    @GetMapping("/add")
    @IsLogin(mustLogin = false)
    public String addCart(
            @CookieValue(value = "cartToken",required = false)String cartToken,
            ShopCart shopCart,
            User user,
            HttpServletResponse response){
        if(null==user){
            if(null==cartToken){
                cartToken= UUID.randomUUID().toString();
                Cookie cookie=new Cookie("cartToken",cartToken);
                cookie.setMaxAge(60*60*24*5);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        cartService.addCart(cartToken,shopCart,user);
        return "cartSuccess";
    }

    @GetMapping("/getList")
    @IsLogin(mustLogin = false)
    @ResponseBody
    public String getList(
            @CookieValue(name = "cartToken",required = false) String cartToken,
            User user){
        System.out.println("获取购物车数据");
        System.out.println(cartToken);
        List<ShopCart> carts = cartService.getList(cartToken, user);
        return "showcarts("+ JSON.toJSONString(carts) +")";
    }

    @GetMapping("/cartList")
    @IsLogin(mustLogin = false)
    public String cartList(@CookieValue(name = "cartToken",required = false) String cartToken,User user, Model model){
        List<ShopCart> carts = cartService.getList(cartToken, user);

        BigDecimal priceall = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : carts) {
            priceall = priceall.add(shopCart.getAllprice());
        }
        model.addAttribute("priceall",priceall);
        model.addAttribute("carts",carts);
        return "showCarts";
    }
}
