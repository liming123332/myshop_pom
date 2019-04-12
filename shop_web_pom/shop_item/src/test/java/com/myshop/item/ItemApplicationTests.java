package com.myshop.item;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileWriter;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApplicationTests {

    @Autowired
    TemplateEngine templateEngine;


    @Test
    public void pp() throws IOException {
        Context context=new Context();
        context.setVariable("msg","helloword");
        templateEngine.process("hello", context,new FileWriter("C:\\Users\\muhuan\\Desktop\\hello.html"));

    }

}
