package com.fmall.content.service;

import com.fmall.common.pojo.EasyUIDataGridResult;
import com.fmall.common.pojo.FmResult;
import com.fmall.pojo.TbContent;

import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/3 18:11
 */
public interface ContentService {
    FmResult addContent(TbContent content);

    FmResult deleteContent(String  ids);

    FmResult updateContent(TbContent content);

    EasyUIDataGridResult getContentPage(long cid, int page, int rows);

    List<TbContent> getContentListByCid(long cid);

    FmResult getContentById(long id);
}
