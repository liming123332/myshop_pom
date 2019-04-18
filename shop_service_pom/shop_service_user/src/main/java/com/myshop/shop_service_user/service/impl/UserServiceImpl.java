package com.myshop.shop_service_user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myshop.entity.User;
import com.myshop.service.IUserService;
import com.myshop.shop_service_user.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User login(String username,String password) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password",password);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public void activeByUsername(String username) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        user.setStatus(1);
        userMapper.update(user,queryWrapper);
}
}
