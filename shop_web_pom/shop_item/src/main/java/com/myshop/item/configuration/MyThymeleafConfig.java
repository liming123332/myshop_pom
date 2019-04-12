package com.myshop.item.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class MyThymeleafConfig {
    @Bean
    public TemplateEngine templateEngine() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("/templates/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setCacheable(false);
        classLoaderTemplateResolver.setCharacterEncoding("utf-8");
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(classLoaderTemplateResolver);
        return engine;
    }
}
