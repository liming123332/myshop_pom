package com.myshop.shop_service_cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.myshop.entity.Good;
import com.myshop.entity.ShopCart;
import com.myshop.entity.User;
import com.myshop.service.ICartService;
import com.myshop.service.IGoodService;
import com.myshop.shop_service_cart.dao.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private IGoodService goodService;

    @Override
    public int addCart(String cartToken, ShopCart shopCart, User user) {

        Good good = goodService.queryById(shopCart.getGid());
        BigDecimal allprice=
                good.getGprice().multiply(BigDecimal.valueOf(shopCart.getGnumber()));
        shopCart.setAllprice(allprice);
        //说明已经登录了 直接将购物车添加进数据库里面
        if(null!=user){
            shopCart.setUid(user.getId());
            cartMapper.insert(shopCart);
        }else{
            redisTemplate.opsForList().leftPush(cartToken,shopCart);
        }
        return 1;
    }

    @Override
    public List<ShopCart> getList(String cart_token, User user) {
        return null;
    }
}
