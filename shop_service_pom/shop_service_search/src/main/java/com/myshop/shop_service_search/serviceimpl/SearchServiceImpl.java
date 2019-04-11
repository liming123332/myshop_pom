package com.myshop.shop_service_search.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.myshop.entity.Good;
import com.myshop.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Good> searchGoods(String keyWords) {
        System.out.println(keyWords);
        SolrQuery solrQuery=new SolrQuery();
        if(keyWords.equals("")){
            solrQuery.setQuery("*:*");
        }else{
            solrQuery.setQuery("gname:"+keyWords+" || "+"ginfo:"+keyWords);
        }
//        SimpleQuery sq=null;
//        if(null==keyWords){
//            sq=new SimpleQuery("*:*");
//        }else {
//            sq = new SimpleQuery("gname:" + keyWords + " || " + "ginfo:" + keyWords);
//        }
//            Page<Good> query = solrTemplate.query(null, sq, Good.class);
//            List<Good> list = query.getContent();

        List<Good> list=new ArrayList<>();
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            SolrDocumentList results = queryResponse.getResults();
            for (SolrDocument result : results) {
                Good good=new Good();
                good.setId(Integer.parseInt(result.get("id")+""));
                good.setGname(result.get("gname")+"");
                good.setGprice(BigDecimal.valueOf(Double.parseDouble(result.get("gprice")+"")));
                good.setGinfo(result.get("ginfo")+"");
                good.setGsave(Integer.parseInt(result.get("gsave")+""));
                good.setGimage(result.get("gimage")+"");
                list.add(good);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int insertSolr(Good good) {
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
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
