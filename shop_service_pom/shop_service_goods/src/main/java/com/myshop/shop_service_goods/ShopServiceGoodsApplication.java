package com.myshop.shop_service_goods;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.myshop.shop_service_goods.dao")
@DubboComponentScan("com.myshop.shop_service_goods.serviceImpl")
public class ShopServiceGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopServiceGoodsApplication.class, args);
    }

}
