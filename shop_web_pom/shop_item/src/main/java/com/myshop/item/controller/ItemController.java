package com.myshop.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.myshop.entity.Good;
import com.myshop.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private TemplateEngine templateEngine;

    @Reference
    private IGoodService goodService;

    @RequestMapping("/createHtml")
    public String createHtml(int gid, HttpServletRequest request){
        Good good=goodService.queryById(gid);
        String outPath=this.getClass().getResource("/static/page/").getPath()+good.getId()+".html";
        Context context=new Context();
        String[] images=good.getGimage().split("\\|");
        context.setVariable("good", good);
        context.setVariable("images", images);
        context.setVariable("contextPath", request.getContextPath());
        try {
            templateEngine.process("goods", context,new FileWriter(outPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
