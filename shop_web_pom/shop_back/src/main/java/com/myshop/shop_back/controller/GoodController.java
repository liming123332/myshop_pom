package com.myshop.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.myshop.entity.Good;
import com.myshop.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/good")
public class GoodController {

    @Reference
    private IGoodService goodService;

    @RequestMapping("/getGoodList")
    public String getGoodList(Model model){
        List<Good> goods = goodService.queryAll();
        System.out.println(goods);
        model.addAttribute("goods",goods);
        return "goodList";
    }
}
