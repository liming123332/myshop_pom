package com.myshop.shop_service_cart;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan("com.myshop.shop_service_cart.service.impl")
public class ShopServiceCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopServiceCartApplication.class, args);
    }

}
