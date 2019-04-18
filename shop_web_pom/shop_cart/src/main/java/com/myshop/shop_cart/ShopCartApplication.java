package com.myshop.shop_cart;

import com.myshop.aop.IsLoginAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopCartApplication.class, args);
    }

    @Bean
    public IsLoginAop getIsLoginAop(){
        return new IsLoginAop();
    }
}
