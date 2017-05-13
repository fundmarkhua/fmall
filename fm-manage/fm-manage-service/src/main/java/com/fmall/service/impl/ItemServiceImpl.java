package com.fmall.service.impl;

import com.fmall.common.pojo.EasyUIDataGridResult;
import com.fmall.common.pojo.FmResult;
import com.fmall.common.utils.IDUtils;
import com.fmall.mapper.TbItemDescMapper;
import com.fmall.mapper.TbItemMapper;
import com.fmall.pojo.TbItem;
import com.fmall.pojo.TbItemDesc;
import com.fmall.pojo.TbItemDescExample;
import com.fmall.pojo.TbItemExample;
import com.fmall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/4/26 21:36
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;

    @Override
    public TbItem getItemById(long itemId) {
        //根据主键查询
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andIdEqualTo(itemId);
        //执行查询
        List<TbItem> list = itemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //查询数据
        TbItemExample example = new TbItemExample();
        List<TbItem> itemList = itemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
        //创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(itemList);

        return result;
    }

    @Override
    public FmResult addItem(TbItem tbItem, String desc) {
        //获取商品id
        final long itemId = IDUtils.genItemId();
        //补全item属性
        tbItem.setId(itemId);
        //商品状态 1-正常 2-下架 3-删除
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //插入数据
        itemMapper.insert(tbItem);

        //创建并写入TbItemDesc数据
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        //插入数据
        itemDescMapper.insert(tbItemDesc);
        //发送消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage(itemId + "");
            }
        });

        return FmResult.ok();
    }

    @Override
    public FmResult updateItem(TbItem tbItem, String desc) {
        tbItem.setUpdated(new Date());
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.createCriteria()
                .andIdEqualTo(tbItem.getId());
        tbItem.setUpdated(new Date());
        itemMapper.updateByExampleSelective(tbItem, tbItemExample);

        TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(tbItem.getId());
        if (tbItemDesc != null) {
            tbItemDesc.setItemDesc(desc);
            tbItemDesc.setUpdated(new Date());
            itemDescMapper.updateByPrimaryKeyWithBLOBs(tbItemDesc);
        }

        return FmResult.ok();
    }

    @Override
    public FmResult getItemDesc(long itemId) {
        TbItemDescExample tbItemDescExample = new TbItemDescExample();
        tbItemDescExample.createCriteria()
                .andItemIdEqualTo(itemId);
        List<TbItemDesc> itemDescList = itemDescMapper.selectByExampleWithBLOBs(tbItemDescExample);
        if (itemDescList.size() > 0) {
            TbItemDesc tbItemDesc = itemDescList.get(0);
            Map<String, String> map = new HashMap<>();
            map.put("itemDesc", tbItemDesc.getItemDesc());
            return FmResult.ok(map);
        }
        return null;
    }

    @Override
    public FmResult updateStatus(List<Long> items, int status) {
        try {
            //设置更新条件
            TbItemExample tbItemExample = new TbItemExample();
            tbItemExample.createCriteria()
                    .andIdIn(items);
            TbItem tbItem = new TbItem();
            tbItem.setStatus((byte) status);
            itemMapper.updateByExampleSelective(tbItem, tbItemExample);
            return FmResult.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return new FmResult(500, "更新失败", null);
        }

    }
}
