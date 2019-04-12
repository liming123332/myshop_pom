package com.myshop.shop_service_goods.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqProviderConfig {
    @Bean
    public Queue getQueue1(){
        return new Queue("good1_queue");
    }

    @Bean
    public Queue getQueue2(){
        return new Queue("good2_queue");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("good_fanoutExchange");
    }

    @Bean
    public Binding getBindingBuilder1(Queue getQueue1,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(getQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding getBindingBuilder2(Queue getQueue2,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(getQueue2).to(fanoutExchange);
    }
}
