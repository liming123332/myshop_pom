package com.myshop.shop_service_search.listener;

import com.myshop.entity.Good;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    @Autowired
    private SolrClient solrClient;

    @RabbitListener(queues = {"good1_queue"})
    public void RabbitMqMsg(Good good){
        SolrInputDocument document=new SolrInputDocument();
        document.setField("id", good.getId());
        document.setField("gname", good.getGname());
        document.setField("ginfo", good.getGinfo());
        document.setField("gprice", good.getGprice().doubleValue());
        document.setField("gsave", good.getGsave());
        document.setField("gimage", good.getGimage());
        try {
            solrClient.add(document);
            solrClient.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
