package com.fmall.search.message;

import com.fmall.common.pojo.SearchItem;
import com.fmall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听商品添加事件
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/13 11:28
 */
public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    Logger logger = LoggerFactory.getLogger(ItemAddMessageListener.class);

    @Override
    public void onMessage(Message message) {
        //取消息内容
        TextMessage textMessage = (TextMessage) message;
        try {
            //从消息中获取商品id
            Long itemId = new Long(textMessage.getText());
            //等待事务提交
            Thread.sleep(1000);
            //根据id查询商品信息
            SearchItem searchItem = itemMapper.getItemById(itemId);
            logger.info(itemId + "");
            //创建一个文档对象
            SolrInputDocument document = new SolrInputDocument();
            //向文档对象中添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            //把文档写入索引库
            solrServer.add(document);
            //提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
