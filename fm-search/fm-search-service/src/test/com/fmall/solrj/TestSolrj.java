package com.fmall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/11 16:22
 */
public class TestSolrj {
   /* @Test
    public void addDocument() throws Exception {
        //建立连接 SolrServer 对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        //创建一个文档对象 solrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域 文档中必须包含一个id域
        document.addField("id", "doc01");
        document.addField("item_title", "丽胖胖的原味内裤");
        document.addField("item_price", 100);
        //文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }
    @Test
    public void deleteDocument() throws  Exception{
        //建立连接 SolrServer 对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        //删除
        solrServer.deleteById("doc01");//
        solrServer.commit();
    }*/

    @Test
    public void queryDocument() throws Exception {
        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("*:*");
        //执行查询
        QueryResponse queryResponse = solrServer.query(query);
        //取文档列表
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("总记录:" + solrDocumentList.getNumFound());
        System.out.println("size:" + solrDocumentList.size());
        //遍历文档列表
        for (SolrDocument document : solrDocumentList) {
            System.out.println(document.get("id"));
            System.out.println(document.get("item_title"));
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_image"));
            System.out.println(document.get("item_category_name"));
        }
    }

    @Test
    public void queryDocumentCustomer() throws Exception {
        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("内裤");
        query.setStart(0);
        query.setRows(20);
        query.set("df", "item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        //执行查询
        QueryResponse queryResponse = solrServer.query(query);
        //取文档列表
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("总记录:" + solrDocumentList.getNumFound());
        System.out.println("size:" + solrDocumentList.size());
        //获取高亮结果
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //遍历文档列表
        for (SolrDocument document : solrDocumentList) {
            System.out.println(document.get("id"));
            List<String> list = highlighting.get(document.get("id").toString()).get("item_title");
            String title;
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = document.get("item_title").toString();
            }
            System.out.println(title);
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_image"));
            System.out.println(document.get("item_category_name"));
        }
    }
}
