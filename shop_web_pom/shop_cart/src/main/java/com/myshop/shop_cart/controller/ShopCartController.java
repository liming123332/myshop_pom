package com.myshop.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.myshop.aop.IsLogin;
import com.myshop.entity.ShopCart;
import com.myshop.entity.User;
import com.myshop.service.ICartService;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
        if(null==cartToken){
            String uuid= UUID.randomUUID().toString();
            Cookie cookie=new Cookie("cartToken",uuid);
            cookie.setMaxAge(60*60*24*5);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        cartService.addCart(cartToken,shopCart,user);
        return "cartSuccess";
    }
}
