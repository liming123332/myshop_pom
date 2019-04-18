package com.myshop.shop_service_user;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan("com.myshop.shop_service_user.service.impl")
public class ShopServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopServiceUserApplication.class, args);
    }

}
