package com.fmall.service;

import com.fmall.common.pojo.EasyUIDataGridResult;
import com.fmall.common.pojo.FmResult;
import com.fmall.pojo.TbItem;

import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/4/27 23:45
 */
public interface ItemService {
    TbItem getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);

    FmResult addItem(TbItem tbItem, String desc);

    FmResult updateItem(TbItem tbItem, String desc);

    FmResult getItemDesc(long itemId);

    FmResult updateStatus(List<Long> items, int status);

}
