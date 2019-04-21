package com.myshop.shop_service_cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myshop.entity.Good;
import com.myshop.entity.ShopCart;
import com.myshop.entity.User;
import com.myshop.service.ICartService;
import com.myshop.service.IGoodService;
import com.myshop.shop_service_cart.dao.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        if(null==shopCart){
            return 0;
        }
        Good good = goodService.queryById(shopCart.getGid());
        BigDecimal allprice=
                good.getGprice().multiply(BigDecimal.valueOf(shopCart.getGnumber()));
        shopCart.setAllprice(allprice);
        //说明已经登录了 直接将购物车添加进数据库里面
        if(null!=user){
            shopCart.setUid(user.getId());
            cartMapper.insert(shopCart);
        }else{
            if(null!=cartToken){
                List<ShopCart> shopCarts = (List<ShopCart>) redisTemplate.opsForValue().get(cartToken);
                if(null!=shopCarts){
                    shopCarts.add(shopCart);
                    redisTemplate.opsForValue().set(cartToken,shopCarts);
                    redisTemplate.expire(cartToken,5, TimeUnit.DAYS);
                }else {
                    shopCarts=new ArrayList<>();
                    shopCarts.add(shopCart);
                    redisTemplate.opsForValue().set(cartToken,shopCarts);
                }
            }
        }
        return 1;
    }

    @Override
    public List<ShopCart> getList(String cartToken, User user) {
        List<ShopCart> carts=null;
        if(null!=user) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid", user.getId());
            carts = cartMapper.selectList(queryWrapper);

            if (null != carts) {
                for (ShopCart cart : carts) {
                    Good good = goodService.queryById(cart.getGid());
                    cart.setGood(good);
                }
                return carts;
            }
        }
        System.out.println(cartToken);
        if(null!=cartToken){
            carts = (List<ShopCart>) redisTemplate.opsForValue().get(cartToken);
            System.out.println(carts);
            if(carts!=null&&carts.size()>0){
                for (ShopCart cart : carts) {
                    Good good = goodService.queryById(cart.getGid());
                    cart.setGood(good);
                }
                return carts;
            }
        }

        return carts;
    }

    /**
     * 合并购物车
     * @param cartToken
     * @param user
     */
    @Override
    public void mergeCarts(String cartToken, User user) {
        if(null!=cartToken){
            List<ShopCart> carts = (List<ShopCart>) redisTemplate.opsForValue().get(cartToken);
            if(null!=carts){
                for (ShopCart cart : carts) {
                    cart.setUid(user.getId());
                    cartMapper.insert(cart);
                }
                //合并完成后清空购物车
                redisTemplate.delete(cartToken);
            }

        }
    }

    @Override
    public void deletCartByUid(int uid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uid", uid);
        cartMapper.delete(queryWrapper);
    }
}
