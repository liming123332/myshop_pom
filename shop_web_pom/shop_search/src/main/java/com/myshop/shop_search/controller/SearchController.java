package com.myshop.shop_search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.myshop.entity.Good;
import com.myshop.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("/searchList")
    public String searchList(String keyWords, Model model){
        System.out.println(keyWords);
        List<Good> list = searchService.searchGoods(keyWords);
        System.out.println(list);
        model.addAttribute("goods",list);
        return "list";
    }
}
