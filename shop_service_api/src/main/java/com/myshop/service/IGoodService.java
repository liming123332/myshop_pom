package com.myshop.service;

import com.myshop.entity.Good;

import java.util.List;

public interface IGoodService {
    public List<Good> queryAll();
    public void insert(Good good);
    Good queryById(int gid);
}
