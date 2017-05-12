package com.fmall.search.dao;

import com.fmall.common.pojo.SearchItem;
import com.fmall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/11 23:53
 */
@Repository
public class SearchDao {
    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws Exception {
        //根据查询条件查询索引库
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果和记录总数
        SolrDocumentList documentList = queryResponse.getResults();
        long numFound = documentList.getNumFound();
        //返回结果对象
        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);
        //创建商品列表
        List<SearchItem> itemList = new ArrayList<>();
        //取商品列表 高亮
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument document : documentList) {
            //获取商品信息
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) document.get("id"));
            searchItem.setImage((String) document.get("item_image"));
            searchItem.setSell_point((String) document.get("item_sell_point"));
            searchItem.setPrice((long) document.get("item_price"));
            searchItem.setCategory_name((String) document.get("item_category_name"));
            //取高亮结果
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            String itemTile;
            if (list != null && list.size() > 0) {
                itemTile = list.get(0);
            } else {
                itemTile = (String) document.get("item_title");
            }
            searchItem.setTitle(itemTile);
            //添加到商品列表
            itemList.add(searchItem);
        }
        //把列表添加到返回结果对象中
        result.setItemList(itemList);
        return result;
    }
}
