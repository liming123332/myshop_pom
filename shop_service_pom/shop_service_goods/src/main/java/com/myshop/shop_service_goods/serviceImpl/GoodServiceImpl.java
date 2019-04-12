package com.myshop.shop_service_goods.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.myshop.entity.Good;
import com.myshop.service.IGoodService;
import com.myshop.service.ISearchService;
import com.myshop.shop_service_goods.dao.GoodMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class GoodServiceImpl implements IGoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Reference
//    private ISearchService searchService;

    @Override
    public List<Good> queryAll() {
        return goodMapper.selectList(null);
    }

    @Override
    public void insert(Good good) {
        goodMapper.insert(good);
        //searchService.insertSolr(good);
        rabbitTemplate.convertAndSend("good_fanoutExchange","",good);
    }

    @Override
    public Good queryById(int gid) {
        return goodMapper.selectById(gid);
    }
}
