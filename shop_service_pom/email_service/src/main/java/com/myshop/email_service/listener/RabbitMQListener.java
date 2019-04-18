package com.myshop.email_service.listener;

import com.myshop.email_service.utils.EmailUtil;
import com.myshop.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {
    @Autowired
    private EmailUtil emailUtil;

    @RabbitListener(queues = {"email_queue"})
    public void emailHander(Email email){
        try {
            emailUtil.sendEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
