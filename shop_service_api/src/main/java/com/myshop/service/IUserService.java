package com.myshop.service;

import com.myshop.entity.User;

public interface IUserService {
    public int register(User user);
    public User login(String username,String password);

    void activeByUsername(String username);
}
