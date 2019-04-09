package com.myshop.shop_service_goods.serviceImpl;

import com.myshop.entity.Good;
import com.myshop.shop_service_goods.dao.GoodMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodServiceImplTest {

    @Autowired
    private GoodMapper goodMapper;

    @Test
    public void queryAll() {
        List<Good> goods = goodMapper.selectList(null);
        System.out.println(goods);
    }

    @Test
    public void insert() {
    }
}