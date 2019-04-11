package com.myshop.service;

import com.myshop.entity.Good;

import java.util.List;

public interface ISearchService {
    /**
     * 通过solr搜索关键字
     * @param keyWords
     * @return
     */
    public List<Good> searchGoods(String keyWords);

    /**
     * solr同步数据库的时候使用
     * @param good
     * @return
     */
    public int insertSolr(Good good);
}
