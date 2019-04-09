package com.myshop.shop_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/toPage")
public class PageController {
    @RequestMapping("/{toPage}")
    public String toPage(@PathVariable("toPage") String toPage){
        return toPage;
    }

}
