package com.fmall.content.service.impl;

import com.fmall.common.jedis.JedisClient;
import com.fmall.common.pojo.EasyUIDataGridResult;
import com.fmall.common.pojo.FmResult;
import com.fmall.common.utils.JsonUtils;
import com.fmall.content.service.ContentService;
import com.fmall.mapper.TbContentMapper;
import com.fmall.pojo.TbContent;
import com.fmall.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/3 18:14
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public FmResult addContent(TbContent content) {
        //补全属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入数据
        contentMapper.insert(content);
        try {
            //缓存同步
            jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
        } catch (Exception e) {
        }

        return FmResult.ok();
    }

    @Override
    public FmResult deleteContent(String ids) {
        String[] strings = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String id : strings) {
            if (StringUtils.isNumeric(id)) {
                idList.add(Long.valueOf(id));
            }
        }
        TbContentExample contentExample = new TbContentExample();
        contentExample.createCriteria()
                .andIdIn(idList);

        //缓存同步
        try {
            //获取将要删除的数据
            List<TbContent> contentList = contentMapper.selectByExample(contentExample);
            for (TbContent content : contentList) {
                jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        contentMapper.deleteByExample(contentExample);
        return FmResult.ok();
    }

    @Override
    public FmResult updateContent(TbContent content) {
        //补全数据
        content.setUpdated(new Date());
        //插入数据
        contentMapper.updateByPrimaryKeySelective(content);
        //缓存同步
        jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
        return FmResult.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(long cid) {

        try {
            String json = jedisClient.hget(CONTENT_LIST, cid + "");
            if (StringUtils.isNotBlank(json)) {
                List<TbContent> contentList = JsonUtils.jsonToList(json, TbContent.class);
                return contentList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据id查询内容列表
        TbContentExample contentExample = new TbContentExample();
        contentExample.createCriteria()
                .andCategoryIdEqualTo(cid);
        List<TbContent> contentList = contentMapper.selectByExample(contentExample);

        //把查询结果添加到缓存
        try {
            jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(contentList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentList;
    }

    @Override
    public EasyUIDataGridResult getContentPage(long cid, int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);

        //查询数据
        TbContentExample contentExample = new TbContentExample();
        if (cid != 0) {
            contentExample.createCriteria()
                    .andCategoryIdEqualTo(cid);
        }
        List<TbContent> contentList = contentMapper.selectByExample(contentExample);

        //获取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);

        //创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(contentList);

        return result;
    }

    @Override
    public FmResult getContentById(long id) {
        TbContent content = contentMapper.selectByPrimaryKey(id);
        return FmResult.ok(content);
    }
}
