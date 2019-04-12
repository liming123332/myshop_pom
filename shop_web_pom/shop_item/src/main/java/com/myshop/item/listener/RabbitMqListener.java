package com.myshop.item.listener;

import com.myshop.entity.Good;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class RabbitMqListener {

    @Autowired
    private TemplateEngine templateEngine;

    @RabbitListener(queues = {"good2_queue"})
    public void CreateHtml(Good good){
        String outPath=this.getClass().getResource("/static/page/").getPath()+good.getId()+".html";
        Context context=new Context();
        String[] images=good.getGimage().split("\\|");
        context.setVariable("good", good);
        context.setVariable("images", images);
//        context.setVariable("contextPath", request.getContextPath());
        try {
            templateEngine.process("goods", context,new FileWriter(outPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
