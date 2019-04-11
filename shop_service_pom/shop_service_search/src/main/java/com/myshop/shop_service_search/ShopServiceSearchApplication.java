package com.myshop.shop_service_search;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan("com.myshop.shop_service_search.serviceimpl")
public class ShopServiceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopServiceSearchApplication.class, args);
    }

}
