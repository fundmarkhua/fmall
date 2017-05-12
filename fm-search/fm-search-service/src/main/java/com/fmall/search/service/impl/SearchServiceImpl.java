package com.fmall.search.service.impl;

import com.fmall.common.pojo.FmResult;
import com.fmall.common.pojo.SearchItem;
import com.fmall.common.pojo.SearchResult;
import com.fmall.search.dao.SearchDao;
import com.fmall.search.mapper.ItemMapper;
import com.fmall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/11 15:25
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchDao searchDao;
    @Value("${DEFAULT_FIELD}")
    private String DEFAULT_FIELD;

    @Override
    public FmResult importAllItems() {
        //查询商品列表
        List<SearchItem> itemList = itemMapper.getItemList();

        try {
            //遍历商品列表
            for (SearchItem item : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档对象中添加域
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                //文档对象写入索引库
                solrServer.add(document);
            }
            //提交
            solrServer.commit();
            return FmResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return FmResult.build(500, "数据导入solr时发生异常");
        }
    }

    @Override
    public SearchResult search(String keyWord, int page, int rows) throws Exception {
        //创建SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.setQuery(keyWord);
        //设置分页条件
        if (page <= 0) {
            page = 1;
        }
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        //设置默认搜索域
        solrQuery.set("df", DEFAULT_FIELD);
        //开启高亮显示
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        //调用Dao执行查询
        SearchResult searchResult = searchDao.search(solrQuery);
        //计算总页数
        long recordCount = searchResult.getRecordCount();
        int totalPage = (int) (recordCount / rows);
        if (recordCount % rows > 0) totalPage++;
        //添加到返回结果
        searchResult.setTotalPages(totalPage);
        //返回结果
        return searchResult;
    }
}
